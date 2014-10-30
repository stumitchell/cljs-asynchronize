(ns cljs-asynchronize.macros)

(defn- add-argument-last [form arg]
  `(~@form ~arg))

(defn- callback [sc fc]
  `(fn [err# res#]
    (cljs.core.async.macros/go
      (if err#
        (cljs.core.async/>! ~fc err#)
        (cljs.core.async/>! ~sc res#)))))

(defn- success-value-or-throw [sc fc]
  `(let [[v# c#] (cljs.core.async/alts! [~sc ~fc])]
      (try
        (if (= c# ~sc)
          v#
          (throw (js/Error. v#)))
        (finally
          (cljs.core.async/close! ~sc)
          (cljs.core.async/close! ~fc)))))

(defn- transform [forms]
  (if (list? forms)
    (if (= (last forms) '...)
        `(let [sc# (cljs.core.async/chan) fc# (cljs.core.async/chan)] ; sc -> success, fc -> fail
           (do 
             ~(add-argument-last (map transform (butlast forms)) (callback sc# fc#))
             ~(success-value-or-throw sc# fc#)))
      (map transform forms))
    forms))

(defmacro asynchronize [& forms]
  `(cljs.core.async.macros/go
    ~@(map transform forms)))
