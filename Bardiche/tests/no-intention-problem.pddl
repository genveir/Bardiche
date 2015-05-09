(define (problem fall-over)
 (:domain no-intention)
 (:objects fred john - character
           ring - item)
 (:init (standing fred)
        (alive fred)
        (standing john)
		(alive john)
		(has fred ring)
		(intends fred (alive fred))
		(intends john (alive john)))
  
 (:goal (has john ring)))