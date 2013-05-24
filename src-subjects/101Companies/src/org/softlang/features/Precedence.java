package org.softlang.features;

import org.softlang.company.*;
import org.softlang.company.impl.bean.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Supervise all salary changes to obey the Precedence feature.
 */
public class Precedence implements Observer {
	
	public void update(Observable o, Object arg) {
		if (o instanceof EmployeeImpl && arg instanceof String) {
			EmployeeImpl e = (EmployeeImpl)o;
			if (((String)arg).equals("salary")) {
				DepartmentImpl d = (DepartmentImpl)e.getParent();
				if (!e.getManager()) {
					// An employee must have a smaller salary than the manager of the department.
					if (e.getSalary() >= d.getManager().getSalary())
						try {
							throw exception(e);
						} catch (Exception e1) {
							//#if Precedence
							e.setSalaryWithOldSalary();
							e.setOldSalary(e.getSalary());
							System.out.println(e1.getMessage());
							//#endif
						}
				} else {
					// A manager of the upper department, if any, must have a greater salary.
					if (d.getParent()!=null && d.getParent() instanceof DepartmentImpl)
						if (e.getSalary() >= ((DepartmentImpl)(d.getParent())).getManager().getSalary())
							try {
								throw exception(e);
							} catch (Exception e1) {
								//#if Precedence
								e.setSalaryWithOldSalary();
								e.setOldSalary(e.getSalary());
								System.out.println(e1.getMessage());
								//#endif
							}
					// All managed employees must have smaller salaries.
					// For sub-departments, the manager is tested only.
					for (Subunit u : d.subunits())
						if (u instanceof DepartmentImpl) {
							if (((DepartmentImpl)u).getManager().getSalary() >= e.getSalary())
								try {
									throw exception(e);
								} catch (Exception e1) {
									//#if Precedence
									e.setSalaryWithOldSalary();
									e.setOldSalary(e.getSalary());
									System.out.println(e1.getMessage());
									//#endif
								}								
						} else {
							if (u!=e && ((EmployeeImpl)u).getSalary() >= e.getSalary())
								try {
									throw exception(e);
								} catch (Exception e1) {
									//#if Precedence
									e.setSalaryWithOldSalary();
									e.setOldSalary(e.getSalary());
									System.out.println(e1.getMessage());
									//#endif
								}								
						}
				}
			}
		}
	}
	
	private Exception exception(Employee e) {
		return new Exception("Precedence constraint violated for employee \"" + e.getName() + "\".");
	} 
}
