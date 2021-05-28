# CowinSlotAlert

# Table of content

- PreRequisite
- FAQ
- Run
- Technical Details

# PreRequisite

- Java 11
- Maven 3.+
- Windows, Mac

# FAQ
 What this app does ?
 
- It alerts you by playing a video on your machine.
 
 Rationale behind the app ?

- Many existing app send out an email or telegram message, which we tend to miss out. So this app will alert you once the slot is available resulting in increase chance of booking.

 What happens once the slot is available ?
- You will be redirected to cowin login page.
- You will be alerted via vide playback.

 How does the app play video on my pc ?

- Since the alert is coming from your own machine. You would need to run it on your machine. It cannot be run centerally like other email alerting apps.

 What is the frequency?

- The app hits cowin server every 2 minutes. 
- This can further reduced to every minutes if required. By changing the frequency property in application.properties to "0 * * * * *"

 What if I want to hit the server every second ?

 - There is a limit on how many times you can hit cowin server. Post which your ip will be blocked temporarily/permanently (not sure of permanent. But yes it will be blocked).
 - We dont want to swarm the server. Be responsible !!
 
 Do I need to enter both pincode & district Id ?
 - No, you only need to enter one of the values. Depending upon whether you want to find vaccination in your pincode or district wide.
 - In case both the values are entered, DistrictId takes precedence!!


# Technical Details

 Why are we fetching the data for current date only and not for future dates?
- The api always returns session for next five day from current date. Hence I am making single call with current date.

 What is the age parameter in application.properties?
- You can configure the parameter as per below info
- 18  : if you want slots for age group [18-44]
- 45  : if you want slots for age group 45+
- leave it blank : if you want slots for age group 18+

 What does the pincode property do ?
- Change this to the pincode where you want to find slots.

 What does the districtId property do ?
- Searches by districtId ex - 3. Note: districtId takes precdence over pincode.

# Run
- mvn clean install
- Change pincode in application.properties to your current location
- Change age to 18, 45 or "" if you want to search for both group 
- Intellij IDE - Run CowinBookingApplication
- java -jar cowinBooking-*.jar
