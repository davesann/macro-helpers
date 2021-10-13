(ns dsann.macros.helpers
 (:require
    [net.cgrand.macrovich :as macros])
 #?(:cljs
     (:require-macros
      ; cljs must self refer macros
      [dsann.macros.helpers :refer [assert-args]])))

(macros/deftime
  (defmacro assert-args
    "Used in macros to assert args meets required form and report errors.
     copied from clojure.core (private). Modified slightly to be host agnostic with ex-info

     takes a seq of pred? msg....
       if any (pred? args) fails, the message and file/line number info will be displayed.

     Example usage:
     (defmacro x [args]
        (assert-args
          (even?    (count args)) \"an even number of forms\"
          (keyword? (first args)) \"the first arg must be a keyword\""
    [& pairs]
    `(do (when-not ~(first pairs)
           (let [ns#          ~'*ns*
                 line#        (:line (meta ~'&form))
                 name#        (first ~'&form)
                 assert-msg#  ~(second pairs)
                 msg#         (str name# " requires " assert-msg# " in " ns# ":" line#)]
             (throw (ex-info msg# {:ns         ns#
                                   :name       name#
                                   :assert-msg assert-msg#
                                   :line       line#}))))
       ~(let [more (nnext pairs)]
          (when more
            (list* `assert-args more)))))


  nil)

