# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

ServerDesign sequence Diagram: 

https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIdTUWjFZfF5vD59SYHYsgoU+aXD0r7lqOH5PF+SwfjAALnAWspqig5QIAePKwvuh6ouisTYgmhgumGbrlCUSAAGaWAatLgaMszQe8JpEuGFoctyvIGoKwowG+hjnDADErq60pJpeyHlORvi8sAMCbGAICpI6zoEsRLIerqmSxv604hsJ5qRhy0YwFp8ZyoRyYISWWE8tmuaYMBIJISUVwDLRY4Tl806zs246tn0-5Xo5hTZD2MD9oOvSuSO76+dWU5Bt586xW2y6rt4fiBF4KDoHuB6+Mwx7pJkmAhReRTUNe0gAKK7lV9RVc0LQPqoT7dF5jboO2gFnECIF9O1c67P59mWVABQEaheW+phU1gDhGL4WZBR6e65E8hwWkBglHVoExTJugUlowGtvJaYlobMQd41mRJUm2tOkBzkpKjLappKQsMEA0JtA3oHtZqqIdHLpp9NDGUGpnIZ2DnlFh+W2QgeajZ2AFXAFFVBV2ORgH2A5DgcZicGl66BJCtq7tCMAAOKjqyhWniV57ME515U3VjX2KObXbU9AEFDDpb9Tzzb-CNvVjfkE0wMgsSYdC814c9KCvZdaknRtQZbXWO3-eGQMSetMAPTtF37SJTngsdd1G0Gj2dQRKtm6SMtgDToyqLCusHfkR3kswwDKjAnPu0r0OjbD8tqHZDmiajpZTMHajjJU-SJwAktIycAIy9gAzAALE8J6ZAaFYTF8OgIKADalxB5dPInAByo7l3sMCNOjxyiVjoXhS+fSJ6oycVKno4Z9neeF1Mxf6m5kFTJX1e1++9cJ6OzejK37dLkTnjpRu2A+FA2DcPAGmGG7hj08VpXM2JzmVLUDQc1zwTC8+vRN3Xi5dfz4f9L9ecnwZgD3Xt-NsMdJY3RgJ6PUsI4DnwVliJWjsAalHVj9d+XsWT62OobY2T0Voo2gZJHw0lbYmwdvkFa6kvSZEvrCL+oxsHqFwbEegRsg6jlDt3AWCC6EoARkjcWsdApXDXqMce5Qc4Fzgl1Tst9cYRVAqAyRmdpGTzkalPeJMAiWBQMqCAyQYAACkIA8mpqOQIi8QANkZtjNkcdqiUjvC0RO3NtZzn7ifYA+ioBwAgGhKAKwADqLA071RaAAIV3AoOAABpL46d1EwBkfnOCndKB-3FqUAB78hpQSrn4gJQTQnhMiTEuJiSG5jxSWkuRkCpYACtzFoFhGYmyKA0QLRQdQt6q11qYM8X9U2ANcHqxtn6O2aBRksW7lLUh5CpmUKWn01WpJ1aXyiZYDOjDaksMBj7YGEzE5CjCEgIS-TiHiStmQ20idek0JAFXJAwwSnQFhD44pgToAHNwQpFAtiYAURgT82AMQeJoGeVsUYlz1kRgtvKUFQSjZUF+Lobgjz+mlGADaA09YGGAL+UcskFIeJuSNtJG0MBIBcNGHoAwPCLI5NMa0oRYtUyiIxmjeR3dFFhTxr0HeK4dEZQCF4XxXYvSwGANgE+hB4iJBSEVM8DiWZpgqNVWq9VGrGF5UBcOpZ4Li0cdA550rPY8JUvC8o5q8CXw9gc1ipRIQgCPi1MGH0vp8UDoPK1MhsUwO4HgEy2hLVEIMi6wF7qkCeo2N6o2gcCHoA0ARDUga7VQANI62ZbpnWupjXG0GPqEDksoKoFoglU1mTDiyxA0r2UOWuQ-HomTMb8r7kKwmK4gA

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
