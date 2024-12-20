## Project Assignment POO - J. POO Morgan - Phase One

## Structure of the project
- src/
    - accounts/
      - Account
    - cards/ - Builder Design Pattern used for generating cards
      - Builder
      - Card
    - clients/
      - Client
      - User
    - commands/ - Command Design Pattern used for commands execution
      - AddAccount
      - AddFounds
      - AddInterest
      - ChangeInterestRate
      - CheckCardStatus
      - Command
      - Commands
      - CreateCard
      - CreateOneTimeCard
      - DeleteAccount
      - DeleteCard
      - PayOnline
      - PrintTransactions
      - PrintUsers
      - Report
      - SendMoney
      - SetAlias
      - SetMinimumBalance
      - SpendingsReport
      - SplitPayment
    - commerciants/ - helper class for commerciants
      - Commerciant
    - system/
      - BankSystem - the most important class that handles the system
      - ExchangeRate - helper class to exchange currencies
    - transactions/ - Abstract ArrayList of type Transaction
      - AddAccountTr
      - AddCardTr
      - CardPaymentTr
      - CardStatusTr
      - ChangeIRTr
      - DeleteAccRemBalTr
      - InsufficientTr
      - DestroyCardTr
      - SendMoneyTr
      - SplitPayError
      - SplitPayTr
      - Transaction
    
## Program Flow

* The program flow starts with the initialization of the BankSystem, which sets up the clients and exchange rates from the provided input data. The system then processes a list of commands using the Commands class, where each command is mapped to a specific operation (such as creating an account, adding funds, or making a payment).

* Each command is executed in sequence, performing the necessary operations on the accounts, cards, and transactions. For example, when a payOnline command is received, the system checks if the card is valid, converts the payment amount to the appropriate currency (if necessary), and updates the account balances accordingly. If an account or card operation fails (like insufficient funds), the system logs an error.

* Throughout these operations, transactions are logged to maintain a record of all actions taken, such as account creations, payments, or interest accrual. The transactions are recorded with a timestamp and relevant details, ensuring a complete audit trail.

* Finally, once all commands have been executed, the system generates a structured output in JSON format, which includes the results of the commands, transaction logs, and the final states of the accounts and cards.

* This flow allows the program to process complex financial actions in a modular and maintainable way while ensuring accurate records and providing clear feedback for each operation.
