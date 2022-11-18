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



## User Stories


- As a user, I want to be able to add a new account that can hold either a negative (borrowed) or positive (owned) 
value (add *Account* into *Master*).
- As a user, I want to be able to enter a financial transaction that is recorded automatically in its associated
accounts (both 'from' and 'to' accounts must be created before transaction entry) and under a category type  
 ( *Earning*, *Spending*, *Transfer*, *Investing*, *Saving*, *Borrowing*, *Lending*). This will be adding 
**Transaction** to both **Account** records and **Master** records
- As a user I want to be able to load the master records from an existing file
- As a new user, I want to be able to create a new master file
- As a user, I want to be able to save the edited master object into the file
- As a user, I want to be able to remove a financial transaction from its associated accounts and master account.
- As a user, I want to be able to remove an account (balance must be zero) 
- As a user, I want to be able to view the current balance of an account 
- As a user, I want to be able to view a list of transaction in an account.
- As a user, I want to be able to view a list of transaction in the master account.







