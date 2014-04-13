(ns ircclient)
(defn initiate-callbacks [callbackObject]
  (defn join [network channel] (.join callbackObject network channel))
  (defn server [network] (.server callbackObject network))
  (.sayHello callbackObject "World of Clojure")
  (server "irc.freenode.org")
  (join "irc.freenode.org" "##programming")
  (join "irc.freenode.org" "#xenotest")
  (join "irc.freenode.org" "#scala")
  (server "irc.efnet.net")
  (join "irc.efnet.net" "#fishing"))

