function main() {
    accounts = new();

    a = newAccount(900);
    b = newLimitedAccount(900, 1000);
    c = newAccount(900);

    println("Balance sheet:");
    a.printBalance(a);
    b.printBalance(b);
    c.printBalance(c);

    println("Giving everyone 500...");
    a.deposit(a, 500);
    b.deposit(b, 500);
    c.deposit(c, 500);

    println("Balance sheet:");
    a.printBalance(a);
    b.printBalance(b);
    c.printBalance(c);
}


// class Account
function newAccount(balance) {
    self = new();
    self.balance = balance;
    self.printBalance = printBalance;
    self.deposit = deposit;

    return self;
}

function printBalance(self) { println(self.balance); }
function deposit(self, num) { self.balance = self.balance + num; }
// end class Account


// class LimitedAccount inherits from Account
function newLimitedAccount(balance, limit) {
    self = newAccount(balance);
    self.deposit = limitedDeposit;
    self.limit = limit;

    return self;
}

function limitedDeposit(self, num) {
    balance = self.balance + num;

    if (balance > self.limit) {
        self.balance = self.limit;
    } else {
        self.balance = balance;
    }
}
// end class LimitedAccount
