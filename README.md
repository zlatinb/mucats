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
