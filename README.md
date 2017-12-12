## This repo contains sample code of a taxi booking web app. 
### Runs on Java EE 6 with Glasshfish 4 Server on NetBeans 8.1. Tested and working. 
### Read below on how to get these files + run them.

### Files Uploaded: 
1. A client application (TaxiBookingSystemClient)
2. An enterprise application (TaxiBookingSystemServer) (containing 1 EJB module & 1 WAR module)
3. A remote interface class for the beans in the above app to call (TaxiBookingSystemServerRemoteInterfaces) 

### How To Start using Git: 
1. Create GitHub account.
2. Fork this repo (top right button) into your account. So now you have an exact copy of the group repo in your personal online repo. 
3. Download your preferred Git desktop client. I recommend [GitKraken](https://www.gitkraken.com/download). 
4. Go back to the "practice-repo" in your personal account. 
5. Get the clone URL.
6. Clone this repository on GitKraken. Cloning just means that now you are "downloading" your online files into an offline location. Choose a folder in your PC to store these files. 

### What Now? 
1. This offline location you have selected to clone your remote repo to is called your "local repo". The repo you just cloned from your account on GitHub is your "remote repo". Lastly, the repo which you have forked from in step 2 of "How To Start" is the "group's remote repo". Let's call it group repo for short. Group's repo = our source code for the whole project. 
2. Now, ANY changes to ANY files in your local repo (create, update, delete), will be automatically detected and shown on GitKraken. 

### Two Basic Processes 
#### I want to push some updates in my code to the project code:
1. Commit changes to local repo. Now, local repo has been updated with the changes.
2. Push local repo to remote repo. Now, your online files on GitHub have been updated with the changes. Can think of it like overwriting your online drive, but this overwriting can be reversed to any point in the project if the code you've submitted online is buggy (see Revert Changes section below).
3. Submit pull request to the group repo. This is a request for the group to review your code and merge your code with the group's codebase once it is verified to be ok (i.e Ask the group to pull your changes).
4. Pull request approved, changes from you are now included in the main codebase. 

#### I want to update my code with the latest project code:
1. Rebase your local repo with group repo. (updates local files with group's files)
2. Push changes in local repo to remote repo. (updates online files with local files)

### Reverting Changes
1. Through reset commit function in GitKraken. Repo can be reset to a point before a certain commit was done.

## Running Sample NetBeans Code
1. Install glassfish server 4 (not 4.1 or 4.1.1) 
2. Start the server, and open admin console in browser by right clicking server.
3. Create JDBC connection pool called PickMe (follow textbook instructions).
4. Create JDBC resource called jdbc/pickme (follow textbook instructions).
5. Shut down server. Run the app (will open up web app on browser). Run the client (will open up menu in console). 
