# MuCats

Website for hosting hashes of cat videos and other files shared on the MuWire network

### Running

1. Check out muwire and execute `./gradlew clean publish` in that project.  You only need to do this step once.
2. Execute
```
./gradlew bootRun
```
Then go to `http://localhost:8080`


### Authentication

Authentication is done through a challenge-response mechanism.  Every MuWire user automatically has an account on MuCats, but they need to prove that fact by signing a randomly-generated challenge with their private MuWire key.  The log in sequence is the following:

1. User enters their full MuWire ID in the form
2. Site generates a random challenge
3. User signs challenge with the MuWire sign tool and copy-pastes it to the response form
4. Site verifies the challenge was signed by the private key of the user.  If ok, user is logged in.

This way there is no need to create user accounts or remember passwords.

### Minimum Viable Product

This is the absolute minimum set of features necessary for someone to be willing to run a site based on the MuCats code.

* Ability to publish hashes of files:
* * The file name and size are specified by the user
* * User can provide a plaintext description
* Users can add comments to the publications of other users
* Users can search by hash or keyword, search covers the description as well
* Moderators can delete individual comments or entire publications
* Administrator (single per site) can appoint users as moderators
* Administrator can ban users
* RSS feed for latest publications

### Features for later
* User avatars
* Markdown in the description of publications
* Forum?  Or maybe just ability to have discussions without associating them with a publication
* Publication ratings
* User can say they have a published file as well
* Integration with mwtrackerd to show number of seeds and leechers
