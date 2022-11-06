# Pawfinder

Pawfinder git link: annakim2022/Pawfinder (github.com) Usage:
• To run the app, you will need to get a bearer token by running the following lines in the terminal:
curl -d "grant_type=client_credentials&client_id=lj86zFOSI7a0wAYDeDtYPKqLhuWDpQ9QCwmLez 0zwAivaSIU3s&client_secret=8DaRw0YlMTrWWeu3oEKYORhnZM20hP4Ya98jtWgp" https://api.petfinder.com/v2/oauth2/token
• This should return a (relatively long) line containing a bearer token that you must then use to replace the token variable in the Second Activity on line 60. This bearer token only lasts for an hour and needs to be replaced if the user continues to use the app for more than that amount of time.
Major Features/Screens:
• First/Main Activity (Start screen):
o Includes a start button to go to the second activity.
• Second Activity (Filter page) *this is where optional feature GPS (getting user location) is implemented and a third party API is used:
o This activity includes a seekbar, checkboxes, and switches to let the user filter certain attributes to find pets to their liking.
o After using the filter’s, the user can click the “Find Pets” button at the bottom to launch the next activity and explore the pets matching the filters they put in.
o If there are no results, the page will not continue to the third activity. This usually means that no pet matches the filters put on.
• Third Activity (Browse page) *this is where optional feature device shake is implemented:
o This activity includes an image of the pet and a list of different attributes below it. Under all that, there is a “More Info” button and a “Next Pet” button.
o Clicking on the “More Info” button will take the user to the fourth activity that contains more information about the pet.
o Clicking on the “Next Pet” button will show another pet fitting the filters of the previous activity (filter page).
• Fourth Activity (More information page):
o This activity includes more information on the pet that was clicked on in the
previous activity (third/swipe activity), it will include things like the distance, coat color, type, age, gender, etc. or N/A if there is no information available on that attribute of the pet.
o At the bottom of the screen, there are two buttons, a “Share” button and a “Contact Organization” button. The “Share” button will take the user to the next activity and the “Contact Organization” button will send a notification to the user. The user can click on the notification and it will take them to the page of the organization that has the animal from this information page.
• Fifth Activity (Share page) *this pretty much is the optional feature open shared activity:
   
o This page contains an area to put your phone number and a message with a small description about the animal to send to a friend.
o At the bottom of the text, there is a “Share” button that will send a message to the phone number the user entered.
Optional Features:
• GPS/Location Awareness (15 points) + Device Shake (10 points) + Light Sensor (10 points) + Using one or more third-party APIs (10 points) + Open shared activity/features (5 points) = 50 points
• GPS/Location Awareness (15 points)
o This will ask the user for their permission to access their location only once, while
using the app, or not at all. If permission is granted to the app, it will take the user’s location and display it in the second activity in longitude and latitude. Then using the seekbar, the user can set a radius for how far they would like to search for a pet.
• Device Shake (10 points)
o In the third activity, the user can shake their device to undo clicking on “Next
Pet”. It will take the user to the previous pet • Light Sensor (10 points)
o This is implemented throughout all the activities. The user can use their android device and depending on the brightness in their room, the sensors will automatically adjust the brightness of the phone for a better quality user experience.
o The user needs to give the app permission to use the light sensor if they have not already done so.
• Using one or more third-party APIs (10 points)
 
o This is used throughout most/all the activities. The API (Petfinder’s Free API for Developers | Petfinder) is at the top of the second activity and gets called there and twice in the fourth. The API is used to get information about the pets including things like their name, age, gender, coat color, picture (if available), and organization (organization is used in the fourth activity where the user wants, they can click on the “Contact Organization” button to go to the website of that pet’s organization)
  
• Open shared activity / features (5 points)
o In the fifth activity, the user can send an SMS message to someone from their android phone.
o Side note, on an emulator, this will not run because emulator’s do not have phone numbers, so it will only run from an actual android device
o If the user enters a 10 digit number, the device automatically assumes it is a legitimate phone number and a “message sent” toast will pop up. Numbers that aren’t 10 digits will get a toast that says input must be 10 digits.
