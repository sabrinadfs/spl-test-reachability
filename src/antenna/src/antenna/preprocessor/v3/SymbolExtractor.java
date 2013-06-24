package antenna.preprocessor.v3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.runtime.tree.Tree;

import antenna.preprocessor.v3.parser.APPLexer;
import antenna.preprocessor.v3.parser.PPLineAST;

public class SymbolExtractor {

	public static Set<String> getSymbols(PPLineAST ast) {

		Set<String> symbols = new HashSet<String>();
		
		extract(ast.getParent(),symbols);
		return symbols;
	}

	private static void extract(Tree ast, Set<String> symbols) {
		int type = ast.getType();
		
		if (type == APPLexer.SYMBOL) {
			String key = ast.getText();
			symbols.add(key);
		} else {
			for (int i = 0; i < ast.getChildCount(); i++) {
				extract(ast.getChild(i),symbols);
			}
		}
	}

}
