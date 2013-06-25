package main;

import java.util.ArrayList;
import java.util.List;

public class MethodScopeInfo {

	public String methodSignature;
	public List<Scope> annotations;
	public int beginLine;
	public int endLine;

	public MethodScopeInfo(String method, int begin, int end) {
		this.annotations = new ArrayList<Scope>();
		this.methodSignature = method;
		this.beginLine = begin;
		this.endLine = end;
	}

	@Override
	public String toString() {
		StringBuffer scopeString = new StringBuffer();

		for (Scope scope : annotations) {
			scopeString.append("\n    > ");
			scopeString.append(scope);
			scopeString.append("\n");
		}

		return "Structure [method=" + methodSignature + ", features="
				+ scopeString.toString() + "]";
	}
}
