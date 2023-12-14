# CS360 Mobile Achitecture & Programming - 23EW2
# Author: Greg Isajewicz 

**Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?**
     The goal of this mobile application titled "EpicStock" is to provide users a solution for tracking and managing inventory in a warehouse, 
   or other relevant environment. In order to fulfill this user need, the application requires at minimum, a databade to store user login credentials
   for login validation, and a database to store the inventory data for the user. The app must of course include a login screen to validate user login, 
   and a grid screen to display the inventory data. The user must be able to add and remove items from the inventory, and edit the quantity of an item. 
   Additionally, users require a mechanism for the app to send SMS alerts when an inventory item quantity is zero, or below som other determined level.

**What screens and features were necessary to support user needs and produce a user-centered UI for the app? How did your UI designs keep users in mind? Why were your designs successful?**
    In addition to the screens mentioned above, the application also includes a rudamentary home screen where users will land once they have logged in. From this
  screen the user can choose to view inventory, request system permission to access SMS function of the mobile device, or logout of the application. In designing 
  each aspect of this mobile app, the goal was a user-centered UI. The designs reflect this in the accessability features, themes, colors, and fonts, all of which 
  come together into a UI the is easy to read and navigate. 
  
**How did you approach the process of coding your app? What techniques or strategies did you use? How could those be applied in the future?**
    My approach to coding this application was incremental. I developed small sections and individual screens at a time, and tested each before moving on to
  the next. This strategy allows for early error/bug detection and correction, preventing the need for major overhauls later in the development process. This 
  strategy and development method should be applied to any future projects as much as possible. 

**How did you test to ensure your code was functional? Why is this process important and what did it reveal?**
    I tested the code to ensure functionality periodically in the development process. Any time a new method or function was implemented or modified, or a file added,
  I would - at minimum - re-run the code to ensure it continued to function. I also used Logcat commands to check the execution of the code at various points. The testing
  process conducted throughout development is important as it helps to avoid major design or development flaws from being overlooked, which could cause major delays or errors
  down the line. Using Logcat to aid in manual testing helped me to more rapidly narrow down the source of bugs or errors that I encountered while developing the application.
  
**Considering the full app design and development process, from initial planning to finalization, where did you have to innovate to overcome a challenge?**
    The only aspect of the development process that I found a need to innovate to some degree was in developing methods for the database of inventory to interact with and 
  dynamically adjust via the InventoryItem class object. The database contains InventoryItem objects. The mobile app pulls from the database to create local arrays of objects
  in inventory upon which the functions of the application work to add, modify, or delete. The challenge was in structuring the methods such that when the user modified an
  object, the database was updated as well as the local array being used as a sort of reference. I believe that using this type of pass-through structure, where the user is never 
  directly feeding input to the database, enables stronger security and easier input validation and checking. 

**In what specific component from your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?**
    I beleive that I was particularly successfull in the development of the database and data structures used within the mobile app for the reasons outlined in the above answer. 
