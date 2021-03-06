/**
 * 
 */
package codemining.ast.java;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.ASTNode;
import org.junit.Before;
import org.junit.Test;

import codemining.ast.TreeNode;
import codemining.ast.java.JavaAstTreeExtractor;
import codemining.java.codeutils.JavaASTExtractor;
import codemining.languagetools.ParseType;

/**
 * @author Miltos Allamanis <m.allamanis@ed.ac.uk>
 * 
 */
public class JavaAstTreeConverterTest {

	private String classContent;
	private String classContent2;
	private String methodContent;

	/**
	 * @param code
	 */
	private void assertRoundTripConversion(final String code,
			final ParseType parseType, final boolean useComments) {
		final JavaASTExtractor ex = new JavaASTExtractor(false,
				useComments);
		final ASTNode cu = ex.getAST(code, parseType);
		final JavaAstTreeExtractor converter = new JavaAstTreeExtractor();
		final TreeNode<Integer> treeCu = converter.getTree(cu, useComments);

		final ASTNode reconvertedCu = converter.getASTFromTree(treeCu);

		assertEquals(cu.toString(), reconvertedCu.toString());
	}

	@Test
	public void checkCrossConversion() {
		assertRoundTripConversion(classContent, ParseType.COMPILATION_UNIT,
				false);

		assertRoundTripConversion(classContent2, ParseType.COMPILATION_UNIT,
				false);

		assertRoundTripConversion(methodContent, ParseType.METHOD, false);
	}

	@Test
	public void checkCrossConversionWithComments() {
		assertRoundTripConversion(classContent, ParseType.COMPILATION_UNIT,
				true);

		assertRoundTripConversion(classContent2, ParseType.COMPILATION_UNIT,
				true);

		assertRoundTripConversion(methodContent, ParseType.METHOD, true);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		classContent = FileUtils.readFileToString(new File(
				JavaAstTreeConverterTest.class.getClassLoader()
						.getResource("SampleClass.txt").getFile()));

		classContent2 = FileUtils.readFileToString(new File(
				JavaAstTreeConverterTest.class.getClassLoader()
						.getResource("SampleClass2.txt").getFile()));

		methodContent = FileUtils.readFileToString(new File(
				JavaAstTreeConverterTest.class.getClassLoader()
						.getResource("SampleMethod.txt").getFile()));
	}

}
