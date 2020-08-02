# And My Code is Ended
Wake back up when your code terminates :)  
(A [Game of Thrones](https://en.wikipedia.org/wiki/And_Now_His_Watch_Is_Ended) pun)  
Check out the backend [here](https://github.com/davidchuyaya/and-my-code-is-ended-backend).


<img src="https://raw.githubusercontent.com/davidchuyaya/and-my-code-is-ended-android/master/Screenshots/App.png" width="256px"/> <img src="https://raw.githubusercontent.com/davidchuyaya/and-my-code-is-ended-android/master/Screenshots/Alarm.png" width="256px"/>

### Story
Back when I believed in machine learning, I'd run some code on servers that should run for 3 days but often `SIGABRT`'d within 3 minutes. Researchers fought heavily for servers, and conference deadlines were days away, so wasting precious ML time was a no-no. Those days, I wished there were an alarm that could wake you up when your code finished running, so I could start my code, go to sleep, and wake up when it finished (or crashed).

Thanks to the coronavirus, I have used the time to make that dream a reality.

## How to use
1. Open the app.
2. Set a ringtone & turn on vibration if you'd like.
3. Copy the code in the app to the end of your code. There's a share button in the app to make the whole ordeal a lot easier. Share the code to Google Keep or something, then open it on your laptop and copy it over to your own code like so:
```bash
$ # my code blah blah blah
$ curl https://us-central1-andmycodeisended.cloudfunctions.net/alarm?token=...
```
4. Run your code.
5. Go to sleep.
6. The app wakes you up when your code is done running. Yay!

You can also set a time-based alarm to wake you up in the app, if your code doesn't terminate by then.

## How it works
1. The app fetches a Firebase Instance ID from Google. This unique ID allows me to send it notifications.
2. You copy the code in the app to the end of your code. That contains a unique token at the end.
3. When your code terminates, it runs: `curl https://us-central1-andmycodeisended.cloudfunctions.net/alarm?token=...`
4. That Firebase Function will send a notification to the phone with whatever ID you gave it.
5. Your phone receives the notification and wakes you up.

## FAQ
#### Q: Why does the alarm only ring ~5 minutes after my code finishes?
A: When your Android phone hasn't been bothered in a while, it enters Doze mode. To preserve battery, the system only wakes up once in a while to check notifications. Thus this app won't see the notification from Firebase until then.

#### Q: What happens when I spam the Firebase Function?
A: The function is running on a free (Spark) plan that allows for 125,000 invocations per month. If it reaches that limit I guess it'll just shut down, idk, so please don't spam the function. Plus your phone might go crazy with all those alarms.

#### Q: How do I set the alarm to go off when my code terminates correctly OR when it crashes?
A: Set up something to catch your code's errors, then also call the Firebase Function there. I never tried it so go figure it out yourself.

#### Q: Sick theme.
A: Thanks bro.
