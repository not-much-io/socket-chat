# socket-chat

This is a simple low level chat, made using sockets.

## Installation

Get the jar in target/uberjar/socket-chat-0.1.0-standalone.jar

## Usage

Running a server:

    $ java -jar socket-chat-0.1.0-standalone.jar -m server -p 9000

Running a client:

    $ java -jar socket-chat-0.1.0-standalone.jar -m client -i 82.131.98.28 -p 9000

## Options

-m = mode, client or server

-i = ip to server, to connect to a server. Server will automatically start on localhost.

-p = port to use for connection

## License

Copyright © 2015

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
