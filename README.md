========================================================================

Saver Sidekick

Authors:Fahim Kaidawala, Brad Jackson, Lucas Spain, Sehun Bang, Katie LI;
========================================================================

------------------
    DESCRIPTION: 
------------------
Saver Sidekick is a mobile app which allows the user to better manage their expenses, budget and saving goals. It includes the following functionalities: allowing user to register/login an account, to view functions by clicking a navigation bar, input weekly income, create new type of income, input/clear budget details and then view the detailed budget details in pie chart, view monthly spending, check period upcoming payment, pinpoint the previous payment with labels and view categorised payment, set/edit/cancel saving goals, import bank statement, add reminder for upcoming payment, see a detailed breakdown of user's average spending and earning per month, manually input transactions, check the financial education resource, view saving in a period of time and etc.

### Dependencies:
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.8.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
implementation 'com.google.firebase:firebase-auth-ktx:21.3.0'
implementation 'com.google.firebase:firebase-auth:21.3.0'
implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.6.1'
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1'
implementation 'androidx.navigation:navigation-fragment:2.5.3'
implementation 'androidx.navigation:navigation-ui:2.5.3'
implementation "org.jetbrains.kotlin:kotlin-stdlib:1.8.10"
implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.10"
implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10"
testImplementation 'junit:junit:4.13.2'
androidTestImplementation 'androidx.test.ext:junit:1.1.5'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
androidTestImplementation 'androidx.test:rules:1.5.0'
androidTestImplementation 'androidx.test.espresso:espresso-contrib:3.5.1'

### Running the app
 Step 1. [Install Android Studio](https://developer.android.com/studio).
Step 2. Download release branch of Saver Sidekick from GitHub.
Step 3. Open the project on Android Studio.
Step 4. Run the app on virtual device.

--------------------------------------
   HOW TO USE LOG IN PAGE
--------------------------------------

1. Launch the application on an Android device or emulator.
2. To log in click on the edit text field and enter a registered email address
3. click the password field or press [tab] and enter your password (at least 7 characters)
4. click the log in button
5. Click the register text to navigate to the register page and sign up.

--------------------------------------
   HOW TO USE REGISTER PAGE
--------------------------------------

1. Launch the application on an Android device or emulator.
2. To register click on the edit text field and enter a email address
3. click the password field or press [tab] and enter your password (at least 7 characters)
4. click the register button.
5. Click the return to log in button to navigate to the log in page.
6. The app should register your email and password to Firebase if correct format/available.

--------------------------------------
   HOW TO USE EARNINGS PAGE
--------------------------------------

1. Launch the application on an Android device or emulator.
2. The EarningsActivity will be displayed, allowing the user to enter their weekly earnings in the provided EditText field.
3. if the user leaves the weekly earnings field empty and clicks the submit button, an error message will be displayed.
4. If the user enters a valid number for their weekly earnings and clicks the submit button, the value will be stored in the application's shared preferences.
5. After submitting the earnings, the HomePageActivity will be launched.
6. Clicking the "Create New Income" button will launch the CreateNewIncome activity.

--------------------------------------
   HOW TO USE EXPENSE CALENDAR
--------------------------------------

1. Launch the application on an Android device or emulator.
2. The CalendarActivity will be displayed, showing a calendar view.
3. The user can click on a specific date to select it.
4. When a date is selected, a TimePickerDialog will be displayed to set the reminder time.
5. After setting the reminder time, an AlertDialog will prompt the user to enter a reminder message.
6. The selected date, reminder time, and reminder message can be used to set a reminder for a specific event or task.
7. The reminder will be triggered at the specified date and time, displaying the reminder message.
8. The user can click the "Set Reminder" button to confirm the reminder, or click "Cancel" to cancel the reminder creation.

--------------------------------------
  HOW TO USE BUDGET EXPENSE ACTIVITY
--------------------------------------

1. Launch the application on an Android device or emulator.
2. The BudgetActivity will be displayed, showing the budget allocation interface.
3. The user can view and interact with different budget categories, such as necessities, wants, and savings.
4. The total weekly earnings and any additional income are retrieved from the shared preferences.
4. The budget amounts for each category are calculated based on the earnings and allocated percentages.
6. The calculated budget amounts are displayed for each category.
7. The progress bars visualize the percentage of the budget allocated to each category.
8. The progress bar colors indicate the level of allocation (e.g., red for low allocation, green for high allocation).
9. The user can adjust the percentages allocated to each category by modifying the calculations in the code.
10. The budget amounts and progress bars update dynamically when the percentages or earnings change.

--------------------------------------
  HOW TO USE NAVIGATION DRAWER
--------------------------------------

1. The HomePageActivity will be displayed, showing the navigation drawer icon in the toolbar.
2. Swipe from the left edge of the screen or tap the navigation drawer icon to open the drawer.
3. The navigation drawer will reveal a list of available app features and options.
4. The user can click on a menu item to navigate to the corresponding feature or screen.
5. The selected menu item will be highlighted to indicate the current active screen.
6. The HomePageActivity class includes a NavigationView.OnNavigationItemSelectedListener implementation to handle menu item clicks and navigate to the appropriate activity.

--------------------------------------
  HOW TO USE ADD NEW INCOME TYPE
--------------------------------------

1. Import the necessary classes or modules
2. Navigate to the Earning Activity page
3. Click on the "Create New Income Type" button
4. Enter the income type (name) and the new income amount
5. Click on the "Add Income" button
6. Validate and display the details of the newly created income
7. Enable the "Cancel" and "Add into Total" buttons


--------------------------------------
  HOW TO USE CHECK EXPENSE PERIOD
--------------------------------------

1. Import the necessary classes or modules
2. Create an instance of the Budget-GraphPage
3. Click on the "Check Expense in a Period Time" button
4. Specify the start and end dates
5. Retrieve the list of upcoming bills
6. Validate and display the list

--------------------------------------
  HOW TO USE PINPOINT PAYMENT WITH LABELS
--------------------------------------
1. Import the necessary classes or modules
2. Navigate to the Home page
3. Click on the "Pinpoint Payment" button
4. Verify that the imported expenses are displayed in the Pinpoint Payment page with their names and amounts
5. Each expense should have a "Pinpoint" button behind it
6. Click on the "Pinpoint" button of the expense for which you want to change the label option
7. Choose a different option label for the selected expense from the dropdown list
8. Verify that the chosen option label is displayed and updated behind the amount of the selected expense

--------------------------------------
  HOW TO USE ADD TRANSACTION
--------------------------------------

1. Navigate to Home page
2. Click 'Add Transaction' button
3. Enter transaction information
4. Click 'Add Transaction' button

--------------------------------------
   HOW TO USE IMPORT CSV FILE
--------------------------------------

In order to import a csv file for transactions, the file must be in the correct format. It must be a .csv, and it must have 3 columns. The first column is for the date which must be in dd/mm/yyyy format, the second column is for the name of the transaction, and the third column is for the amount. Positive values will be added as income and negative values will be added as expenses.

1. Navigate to Home page
2. Cick 'Import CSV File' button
3. Select file

--------------------------------------
   HOW TO USE GOALS PAGE
--------------------------------------

1. Open navigation sidebar
2. Click 'Goals'
3. Click 'New Goal' button
4. Enter goal information
5. Click 'Add Goal' button
6. Goal will be displayed on goals page
7. Click 'Edit Goal' button
8. Change goal information
9. Click 'Add goal' button

--------------------------------------
   HOW TO USE MONTHLY STATS
--------------------------------------

1. Navigate to graph page
2. Click 'More Info' button
3. Monthly stats page will be displayed
--------------------------------------
   HOW TO USE ADD OVER WORKED HOUR
--------------------------------------
1. Click 'OVERWORKEDHOUR' button
2. Enter "hours worked" and "Bonus ratio" information
3. Click submit.
4. click submit to add to the overall income
5. Click edit to change.
--------------------------------------
   HOW TO USE CREDIT CARD
--------------------------------------
1. Open navigation sidebar
2. Click 'CREDIT CARD'
3. Enter creadit card information
5. Click 'add' button
6. Credit card details will show up.
7. Click 'edit' button to change
8. Click 'Terms and Policy' button to check the Terms and Policy
9. Click 'Home' to go back to homepage
--------------------------------------
   HOW TO USE SAVINGS HISTORY GRAPH
--------------------------------------
1. Click 'Savingshistory' button
2. Enter the "starting month", "how many month"(for graph to display) and "this month savings"information
3. Click 'Home' to go back to homepage
--------------------------------------
   HOW TO USE FINANCIAL EDUCATION RESOURCE
--------------------------------------
1. Click 'Savingshistory' button
2. Click 'FINAICAL EDUCATION RESOURCE' button
3. Click "Politechic" the button to read "Politechic"
3. Click "MoneyHub" the button to read "MoneyHub"
3. Click "ReservedBank" the button to read "ReservedBank"
3. Click the "Home "button to return HomePage

--------------------------------------
   HOW TO USE SPENDING ALERT PAGE
--------------------------------------

1. Click the Spending Alert Button on the main page
2. In the spending alert activity, the user can set spending alerts for their personal high-spend times of the day.
3. Example high-spend times are listed for the user
4. scroll down to set the notifications to a certain hour, minute and second of the day and click the button.
5. The button will show how many seconds away the notification will show.
6. The user can set three different notifications for three different high-spend times.  

--------------------------------------
   HOW TO USE CRYPTO PRICE PAGE
--------------------------------------
 
 1. Click the Crypto Price Button on the main page
 2. In the CyptoPriceActivity the user can see today’s updated cryptocurrency prices for most of the main available coins displayed in a list from the CoinCap API.
 3. The details displayed are the name of the coin, the coin’s symbol/code, the price of the coin (USD), and the percentage difference in the past 24 hours.
 4. The user can scroll through the list and check these details.  

 --------------------------------------
   HOW TO USE BUDGET EXPENSE PAGE
--------------------------------------

 1. Click the Budget Button on the home page
 2. Here the user can set the different upcoming expenses by clicking the add new button they can create a list which shows the name of the expense, the amount of the expense and when it is due.
 3. These expenses are displayed in a card list which can be deleted individually.
 4. Each list is saved to the user for further use.
 5. Once these list items are inputted they can be viewed in a graph format or cleared to start a new list with the buttons at the bottom of the screen.
