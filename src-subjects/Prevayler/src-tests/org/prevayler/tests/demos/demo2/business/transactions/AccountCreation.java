package org.prevayler.tests.demos.demo2.business.transactions;
import java.util.Date;

import org.prevayler.tests.demos.demo2.business.Account;
import org.prevayler.tests.demos.demo2.business.Bank;
public class AccountCreation extends BankTransaction {
  private static final long serialVersionUID=476105268406333743L;
  private String _holder;
  private AccountCreation(){
  }
  public AccountCreation(  String holder){
    _holder=holder;
  }
  protected Object executeAndQuery(  Bank bank,  Date ignored) throws Account.InvalidHolder {
    return bank.createAccount(_holder);
  }
}
