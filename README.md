# cowinSlotAlert

- What this app does ?
 
 It Alerts you by making a sound once the slot is available. In this case it plays a youtube video.
 
 - Rationale behind the app ?

Many existing app send out an email or telegram message, which we tend to miss out. So this app will alert you once the slot is available resulting in increase chance of booking.

- How does the app play video on my pc ?

Since the alert is coming from your own machine. You would need to run it on your machine. It cannot be run centerally like other email alerting apps.

- What is the frequency?

The app hits cowin server every 2 minutes. This can be reduced to every minutes is required.
By changing the frequency property in application.properties to "0 * * * * *"

- What if I want to hit the server every second ?

There is a limit on how many times you can hit cowin server. Post which your ip will be blocked probably temperoraily.
We dont want to swarm the server. Be responsible !!


Technical Details -

- Why are we fetching the data for current date only and not for future dates?
The api always returns session for next five day from current date. Hence I am making single call with current date.

- What is the age limit parameter in application.properties?
Filter the data as per center min age limit. 
For example if you want to find slots for 45  above set age=45
If you want to find slots for 18 above set age=18
If you want to find for both age group you should comment this property.

- What does the pincode property do ?
Change this to the pincode where you want to find slots.



