# Personal Finance Manager

## Overview

**What will the application do?**

The application will track all financial transactions of a user through user input as a transaction entry, organize and
record them in their respective categories and provides default and user-specified report tables and visualizations
from different categories and account to inform the user about specific aspect of their finances or their overall
financial status (more details in the following overview section).


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
Transaction fall in following 6 categories but can have further sub-categories to allow for more detailed report:

1. Earning
2. Spending
3. Investing
4. Saving
5. Borrowing
6. Lending

Furthermore, the user's finances are allocated between different accounts with different specification. An account holds
a value that can be positive or negative based on its specification. The default account at initiation stage is
Cash account with zero balance. A user can create multiple accounts to represent and track specific allocations such as
following accounts which is not exhaustive:

- Cash (default)
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

Any financial transaction must move value from one account to another with following exceptions:

1. Earning only adds value to one account
2. Spending only subtract value from one account

Accounts that represent bank accounts, wallet, investment, and lending must have positive (or zero) values at all time
and accounts that represent credit cards, loans and personal borrowing must have a negative (or zero)  values at all
times. User can choose "from" and "to" accounts (except earning and spending transactions that will have only "to" or
"from" account), transaction category, the value, date, and detail (optional) to enter a transaction. The application
will categorize the transaction based on the inputs, record them in its respective accounts, and add or subtract the
value from respective account balances.

And finally, user can create reports in the table format or visualization in pie chart or time graph. For each 
transaction category, sum or mean values can be reported. A user can choose overall, yearly, monthly, weekly, daily, 
or a custom time period. A user can also produce the visualization of the chosen category over time. A snapshot report 
of all categories also can be produced to indicate the overall financial allocation of the user in a specified time frame.
A similar reports can be produced based on the accounts and pie charts can be produced to display the relative 
proportions of resource allocations.


## User Stories


- As a user, I want to be able to add a new account  that can hold either a negative (borrowed) or positive (owned) 
value (add *Account* into *User*).
- As a user, I want to be able to enter a financial transaction that is recorded automatically in its associated 
category (add a *Transaction* into one of *Earning*, *Spending*, *Investing*, *Saving*, *Borrowing*, *Lending*) and 
into "from" and "to" account(s).
- As a user, I want to be able to remove a financial transaction from its associated category and accounts.
- As a user, I want to be able to remove an account (balance must be zero) 
- As a user, i want to be able to view the current balance of an account (sum of transaction values)
- As a user, I want to be able to view a snapshot table of all account balances.
- As a user, I want to be able to view a list of transaction in an account.
- As a user, I want to be able to view a list of transaction in a transaction category.
- As a user, I want to be able to view a pie chart of balances (positive) across different accounts
- As a user, I want to be able to view a pie chart of borrowings (negative) across different accounts
- As a user, I want to be able to view a histogram of different categories for user-specified time bins and period.







