## This repo contains sample code of a taxi booking web app. 
### Runs on Java EE 6 with Glasshfish 4 Server, with JDK 1.8 on NetBeans 8.1. Tested and working. 
### Read below on how to get these files + run them.

### Files Uploaded: 
1. A client application (TaxiBookingSystemClient)
2. An enterprise application (TaxiBookingSystemServer) (containing 1 EJB module & 1 WAR module)
3. A remote interface class for the beans in the above app to call (TaxiBookingSystemServerRemoteInterfaces) 

### How To Start using Git:
1. Create GitHub account.
2. Fork this repo (top right button) into your account. So now you have an exact copy of the group repo in your personal online repo. 
3. Download your preferred Git desktop client. I recommend [GitKraken](https://www.gitkraken.com/download). 
4. Go back to the "practice-repo" in your **_personal account_** (not the group's one). 
5. Get the clone URL.
6. Clone this repository on GitKraken. Cloning just means that now you are "downloading" your online files into an offline location. Choose a folder in your PC to store these files. 
7. Add the group's online repo as a remote repo. Go to GitKraken and click the '+' sign beside 'Remote'. Select the group's repo & and edit the name if you wish for your own clarity (maybe something like 'group repo')
**8. Now you will have 2 remote repos, 1 personal & 1 group. You will use the personal repo to sync your code between offline & online. The group repo should only be used by the group as a storage for the latest working codebase. It exists here on GitKraken so that you can easily see the current state of the group repo compared to your personal repo on the tree diagram. You can only create a pull request from personal repo to group repo through drag and drop in GitKraken (read below).**

### What Now? 
1. This offline location you have selected to download your online files into is called your **_local repo_**. The repo you just cloned from your account on GitHub is your **_remote repo_**. Lastly, the repo which you have forked from in step 2 of "How To Start". is the **_group's remote repo_**. Let's call it **_group repo_** for short. **Group's repo = our source code for the whole project.** 
2. Now, ANY changes to ANY files in your local repo (create, update, delete), will be automatically detected and shown on GitKraken. 

### 3 Basic Processes 
#### 1. I want to push some updates in my code to the project code:
1. Commit changes to local repo. Now, local repo has been updated with the changes.
2. Push local repo to remote repo. Now, your online files on GitHub have been updated with the changes. Can think of it like overwriting your online drive, but this overwriting can be reversed to any point in the project if the code you've submitted online is buggy (see section 3 below).
3. Submit a pull request to the group repo. On the tree, drag the icon of your personal repo to the group repo icon. This is a request for the group to review your code and merge your code with the group's codebase once it is verified to be ok (i.e Ask the group to pull your changes).
4. Once pull request approved by admin, changes from you are now included in the main codebase. 

#### 2. I want to update my code with the latest project code:
1. Rebase your local repo with group repo. (updates local files with group's files)
2. Push changes in local repo to remote repo. (updates online files with local files)

#### 3. I want to reverse some changes (buggy code/mistakes etc.):
1. Find the commit(s) you want to undo. Right click the commit(s) on the GitKraken tree diagram. Click Revert commit.
2. After all intended commit(s) have been reverted, perform the same procedure as pushing updates (section 1 above). Push to online repo & make a pull request to the group repo. 
3. Once admin approves the pull request, the commit(s) will now be undone on the group repo too. 
#### Note: Think of reverting commits as a set of undo operations. Like if you have pushed a buggy file online, reverting this commit will just delete that buggy file. 

## Running Sample NetBeans Code
1. Install glassfish server 4 (not 4.1 or 4.1.1) 
2. Start the server, and open admin console in browser by right clicking server.
3. Create JDBC connection pool called PickMe (follow textbook instructions).
4. Create JDBC resource called jdbc/pickme (follow textbook instructions).
5. Shut down server. Run the app (will open up web app on browser). Run the client (will open up application menu in console). 
