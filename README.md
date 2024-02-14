## CS4520 Assignment 1: Amazing Products

Link to GitHub repository: https://github.com/Stephanie-Delicia/cs4520-assignment1

### To use:

I was able to run the app on my end and included a video (assignment1_demo.mov) of how the app functions. 
So, I believe downloading the code and running it should work with Android 14.

The LoginFragment, ProductListFragment, and navigation should function as per assignment requirements. 

### Added files

#### 1. MainActivity

#### 2. Fragments

In folder, fragments, two new fragments were added: LoginFragment and ProductListFragment.

LoginFragment has two text fields and a button. ProductListFragment just displays a list of products.

#### 3. RecyclerAdapter

An adapter for displaying products in a list in the ProductListFragment. Obtains data from Dataset.kt from the original repo.

Dataset.kt contains the seal classes for different product types.

#### 4. Layout

This folder contains the layouts for the new fragments, main activity, and the card layout for the product list.


#### 5. Navigation

Finally, there is an xml called nav_graph in the navigation folder for naviagting from the login to the product list. 
