## CS4520 Assignment 4: API Adventures

Link to GitHub repository: https://github.com/Stephanie-Delicia/cs4520-assignment1

### To use:

I was able to run the app on my end and included a video (assignment4_video.mov) of how the app functions. 
So, I believe downloading the code and running it should work with Android 14.

Overall, I believe I implemented the required functionality for the most part. The only exception
is when the product list is saved into the database and displayed to the user again in airplane mode.
The products are not displayed in the original order. This should be a quick fix, since this is
due to the database returning data in the order of object ids, so I should use a different id scheme. 

### Added files

#### 1. APIProduct & APIService

These files contain the logic for how API information is fetched.

#### 2. ProductDAO, ProductDB

ProductDB is the database containing product information whereas
ProductDAO describes what methods can be applied to the database,
for retrieving info, deleting data, etc.
