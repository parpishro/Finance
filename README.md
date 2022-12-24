# Personal Finance Manager

## Overview

**What will the application do?**

The application will track all financial transactions of a user through user input as a transaction entry, organize and
record them in their respective accounts and provides default and user-specified report tables and visualizations
from different accounts to inform the user about specific aspect of their finances or their overall financial status 
(more details in the following overview section).


**Who will use it?**

The application is designed for personal use by an individual user. However, this application can also be used to manage
the finances of a family or any small-scale entity.


**Why is this project of interest to you?**

I have always wanted to have a some application to track all my finances across different accounts and categories. Some
corporate accounts such as investment and banking application provide a limited version of this service, but their
service is platform-specific. There are also some third-party applications that provide this service but the way in
which they preserve the privacy of user is not acceptable or questionable at best. Financial data is among most
sensitive personal data and is sought actively by a variety of entities. For mentioned reasons, it is almost impossible
to record and track financial transactions in a single application safely and privately. This application will allow to
save and manage my financial data on my personal storage. It also provides different tables and visualizations to track
certain aspect of my finances or my overall financial status.


## Structure

This application will record financial transactions of a user and record them in their respective categories. 
Transaction fall in following 7 categories but can have further sub-categories to allow for more detailed report:

1. Earning
2. Spending
3. Transfer
4. Investing
5. Saving
6. Borrowing
7. Lending

Furthermore, the user's finances are allocated between different accounts with different specification. An account holds
a value that can be positive or negative based on its specification. A user can create multiple accounts to represent 
and track specific allocations such as following accounts which is not exhaustive (they are created by user when needed)

- Cash
- Checking Bank Account
- Saving Bank Account
- Credit Card
- Line of Credit
- Digital Wallet (Venmo, PayPal, ...)
- Bank Loan
- Stock Investment
- Cryptocurrency Investment
- Commodity Investment
- Personal Borrowing
- Personal Lending

Any financial transaction must move value from one account to another with following exceptions. This hypothetical 
accounts will be created at the beginning to enable earning and spending entries. Their balance represent total earnings
and total spending:

1. Earning bring in value from a hypothetical account "Income"
2. Spending take out value to a hypothetical account "Expenditure"

Accounts that represent bank accounts, wallet, investment, and lending must have positive (or zero) values at all time
and accounts that represent credit cards, loans and personal borrowing must have a negative (or zero)  values at all
times. User can choose "from" and "to" accounts, transaction category, the value and date to enter a transaction. The 
application will enter the transaction into respective 'from' and 'to' accounts based on the inputs, and also record it
in a master account that holds all transactions. In addition to creating accounts, user can edit account information.

Finally, user can create reports in the table format or visualization (later will be implemented with gui) in pie 
chart or time graph. A user can choose to print out balance, last 20 transaction and all transactions for any specific
account or for master account. A user can also produce the visualization of the chosen category or account (phase > 1).




**Instructions for Starting the App:**

*When starting application, you can load an existing user's data ("Par" is the name of the user that has its Json data 
in the project) or you can create a new user*

- You can generate the first required event related to adding accounts to a master by choosing add account from the menu. 
  Then a pop-up menu will open for you to enter the account details. After selecting Ok, the new account will be added
  to the account list in the top left of the main page. 
- You can generate the second required event related to adding Transaction entry to an account by selecting "add 
  transaction" to the account, then a pop-up will open and after entering the info and selecting ok, it will be added to
  the transaction list at the bottom of the main page.
- You can locate my visual component in the beginning of the app as splash screen which stays for 5 second before 
  initiating the app. Moreover, I have an icon image that is used in dialog boxes. Both images are in data folder.
- You can save the state of my application by selecting "save" from the menu bar at the top.
- You can reload the state of my application by selecting an existing username and then enter the username (currently 
  Par) which will load the master file for Par. Alternatively you can create a new username.


**Example of Log**

*Please note that when application is started and accounts are loaded, the LogEvent class logs the existing accounts
as they are added to the master. Therefore, below log represents both persistence and application operations.*


Cash account was added and total balance was updated

Fri Dec 02 21:24:39 PST 2022
Expense account was added and total balance was updated

Fri Dec 02 21:24:39 PST 2022
Saving account was added and total balance was updated

Fri Dec 02 21:25:16 PST 2022
New transaction entry was added to allTransaction and total balance was updated

Fri Dec 02 21:25:16 PST 2022
A transaction entry was added to Cash account and its balance was updated

Fri Dec 02 21:25:16 PST 2022
A transaction entry was added to Expense account and its balance was updated

Fri Dec 02 21:25:52 PST 2022
New transaction entry was added to allTransaction and total balance was updated

Fri Dec 02 21:25:52 PST 2022
A transaction entry was added to Cash account and its balance was updated

Fri Dec 02 21:25:52 PST 2022
A transaction entry was added to Saving account and its balance was updated

Fri Dec 02 21:26:26 PST 2022
Income account was added and total balance was updated

Fri Dec 02 21:28:01 PST 2022
New transaction entry was added to allTransaction and total balance was updated

Fri Dec 02 21:28:01 PST 2022
A transaction entry was added to Income account and its balance was updated

Fri Dec 02 21:28:01 PST 2022
A transaction entry was added to Saving account and its balance was updated



**Future Modifications**

- There are quite a bit of duplication between Transaction and Account classes' methods and fields.
- There are also duplication between Account and Master classes' methods and fields.
- If I had more time, I would refactor those into an interface, or perhaps an abstract class.
- The final, FinAppGUI class contains all the behaviours of the panes, and it has become very large.
- If I had more time, I would make different classes for balance, transaction, account panes, and save and print options.
- Despite reducing the dependency between classes as much as I could, there are still some of unnecessary dependency.
- Because of the way I implemented persistence, I had to reimplement some model classes' methods with different 
 arguments. If I had more time, I would refactor those to reduce the duplication.


