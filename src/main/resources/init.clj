(ns ircclient)
(defn initiate-callbacks [callbackObject]
  (println callbackObject)
  (.sayHello callbackObject)
  (.sayHello callbackObject "world")
  )

; (initiate-callbacks "hello from resource")
; (println "hello from resources")