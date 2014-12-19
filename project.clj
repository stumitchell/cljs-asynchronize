(defproject cljs-asynchronize/cljs-asynchronize "0.1.1-SNAPSHOT"
  :source-paths ["src"]
	:description "Asynchronize callback code"
	:url "https://github.com/gilbertw1/cljs-asynchronize"
	:license {:name "Eclipse Public License"
			:url "http://www.eclipse.org/legal/epl-v10.html"}
	:plugins [[lein-cljsbuild "0.3.2"]
            [org.bodil/lein-noderepl "0.1.10"]]
	:cljsbuild {
		:builds [{
			:source-paths ["src"]
			:compiler {
				:target :nodejs
				:optimizations :simple
				:pretty-print true}}]}
	:dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.nrepl "0.2.3"]])