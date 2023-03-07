# Pinger Android Code Challenge

This repo contains code that solved the following challenge,
> Please create an Android app that downloads a text file, parses the contents, and displays the results. You may retrieve the file [here](https://raw.githubusercontent.com/cplachta-pinger/android_coding_challenge/master/Apache.log). The file is a standard Apache web server access log. We’re interested in knowing the most common three-page sequences in the file. A three-page sequence is three consecutive requests from the same user. In case it helps simplify your processing, you can assume the following about the contents:
> -   Log entries are in ascending chronological order
> -   An IP address is unique per user
>
> Please present the results in descending order by sequence frequency, so the most common sequence appears first and the least common appears last. Each sequence should display the pages that make up the sequence and the number of times that the sequence appears.
>
> Example
>
> Based on the following sample data, sequence “Page 1, Page 2, Page 3” is the most common. It appears twice in the results:
>
> User A: Page 1
>
> User B: Page 1 User B: Page 2 User B: Page 3 User B: Page 2
>
> User A: Page 2 User A: Page 3 User A: Page 4 User A: Page 1 User A: Page 2


## Objective

1. Update this code to use more recent libraries and patterns.
    - Convert the XML views to use Compose.
    - Convert RxJava usage to Flow
    - Update anything else you see fit

2. The current code used an MVP (Model-View-Presenter) architecture pattern to solve the challenge above. Convert the architecture to use an MVI  (Model-View-Intent) approach.


## Return

Return to us either as a Public Repo or .zip of the android studio project (You may need to share a google drive link if the project is too large). Thank you.
