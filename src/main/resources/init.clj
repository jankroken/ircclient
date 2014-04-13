(ns ircclient)
(defn initiate-callbacks [callbackObject]
  (defn join [network channel] (.join callbackObject network channel))
  (.sayHello callbackObject "World of Clojure")
  (join "irc.freenode.org" "##programming"))
