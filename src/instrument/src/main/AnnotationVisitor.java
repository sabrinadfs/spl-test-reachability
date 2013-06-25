package main;

import java.util.List;

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

public class AnnotationVisitor extends VoidVisitorAdapter<List<MethodScopeInfo>> {
	
	public List<Scope> scopes;
	
	public AnnotationVisitor(List<Scope> scopes) {
		super();
		this.scopes = scopes;
	}
	
	public void visit(MethodDeclaration mdec, List<MethodScopeInfo> structureInfo) {
		int begin = mdec.getBeginLine();
		int end = mdec.getEndLine();
		String signature = mdec.getName() + "(" + mdec.getParameters() + ')';
		
		MethodScopeInfo st = new MethodScopeInfo(signature, begin, end);
		structureInfo.add(st);
		addContainedAnnotations(st);
	}
	
	private void addContainedAnnotations(MethodScopeInfo st) {
		for (Scope scope : scopes) {
			if (st.beginLine < scope.end &&
				scope.begin < st.endLine ) {
				st.annotations.add(scope);
			}
		}
	}
}
