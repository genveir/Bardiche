(define (domain door)
 (:requirements :adl :domain-axioms :intentionality :expression-variables :bardiche)
 (:types rope door - item 
         character room - object)
 
 (:predicates (has ?character - character ?item - item)
              (at ?character - character ?room - room)
              (in ?item - item ?room - room)
              (adjacent ?room - room ?neighbor - room)
              (between ?door - door ?from - room ?to - room)
              (climbable ?from - room ?to - room)
              (open ?door - door)
              (can_open ?character - character ?door - door)
              (shop ?room - room ?item - item)
              (rich ?character - character)
              (bribed ?character - character)
              (dead ?character - character)
              (killed ?killer - character ?victim - character)
              (together ?character - character ?other - character)
              (together_and_alive ?character - character ?other - character)
              (barricaded ?door - door)
  )
 
 (:action move
   :parameters      (?character - character ?from - room ?to - room)
   :precondition    (and (at ?character ?from) 
                         (adjacent ?from ?to)
                         (not (= ?from ?to))
                         (not (dead ?character)))
   :effect          (and (not (at ?character ?from))
                         (at ?character ?to))
   :agents          (?character)
   :initiator       (?character))
   
 (:action enter
    :parameters     (?character - character ?from - room ?to - room ?door - door)
    :precondition   (and (at ?character ?from)
                         (in ?door ?from)
                         (between ?door ?from ?to)
                         (open ?door)
                         (not (dead ?character)))
    :effect         (and (not (at ?character ?from))
                         (at ?character ?to))
    :agents         (?character)
    :initiator      (?character))
   
  (:action climb
    :parameters     (?character - character ?from - room ?to - room ?rope - rope)
    :precondition   (and (at ?character ?from)
                         (climbable ?from ?to)
                         (has ?character ?rope)
                         (not (dead ?character)))
    :effect         (and (not (at ?character ?from))
                         (at ?character ?to)
                         (not (has ?character ?rope)))
    :agents         (?character)
    :initiator      (?character))
   
  (:action barricade
    :parameters     (?character - character ?door - door ?room - room)
    :precondition   (and (at ?character ?room)
                         (in ?door ?room)
                         (not (open ?door))
                         (not (dead ?character)))
    :effect         (barricaded ?door)
    :agents         (?character)
    :initiator      (?character))
    
  (:action open
    :parameters     (?character - character ?door - door ?room - room)
    :precondition   (and (at ?character ?room)
                         (in ?door ?room)
                         (not (barricaded ?door))
                         (can_open ?character ?door)
                         (not (dead ?character)))
    :effect         (open ?door)
    :agents         (?character)
    :initiator      (?character))
    
  (:action buy
    :parameters     (?character - character ?room - room ?item - item)
    :precondition   (and (at ?character ?room)
                         (shop ?room ?item)
                         (rich ?character)
                         (not (dead ?character)))
    :effect         (and (not (rich ?character))
                         (not (shop ?room ?item))
                         (has ?character ?item))
    :agents         (?character)
    :initiator      (?character))
    
  (:action kill
    :parameters     (?killer - character ?victim - character)
    :precondition   (together_and_alive ?killer ?victim)
    :effect         (and (dead ?victim)
                         (killed ?killer ?victim))
    :agents         (?killer)
    :initiator      (?killer))
    
  (:action bribe
    :parameters     (?character - character ?other - character ?intention - expression)
    :precondition   (and (rich ?character)
                         (together_and_alive ?character ?other)
                         (intends ?character ?intention)
                         (not (intends ?character (not ?intention))))
    :effect         (and (not (rich ?character))
                         (bribed ?other)
                         (intends ?other ?intention))
    :agents         (?other)
    :initiator      (?character))
    
  (:axiom
    :vars           (?room - room ?neighbor - room)
    :context        (adjacent ?room ?neighbor)
    :implies        (adjacent ?neighbor ?room))
    
  (:axiom
    :vars           (?character - character ?other - character ?room - room)
    :context        (and (at ?character ?room)
                         (at ?other ?room)
                         (not (= ?character ?other)))
    :implies        (together ?character ?other))
    
  (:axiom
    :vars           (?character - character ?other - character ?room - room)
    :context        (and (at ?character ?room)
                         (at ?other ?room)
                         (not (= ?character ?other))
                         (not (dead ?character))
                         (not (dead ?other)))
    :implies        (together_and_alive ?character ?other))
    
  (:axiom
    :vars           (?character - character ?other - character ?room - room)
    :context        (and (at ?character ?room)
                         (not (at ?other ?room)))
    :implies        (and (not (together ?character ?other))
                         (not (together_and_alive ?character ?other))))
                         
  (:axiom
    :vars           (?character - character ?other - character)
    :context        (or (dead ?character)
                        (dead ?other))
    :implies        (not (together_and_alive ?character ?other)))
    
  (:axiom
    :vars           (?character - character ?other - character ?door - door)
    :context        (and (killed ?character ?other)
                         (can_open ?other ?door))
    :implies        (can_open ?character ?door))
)