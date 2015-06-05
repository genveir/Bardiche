package com.geertenvink.Bardiche;

public class BardicheGoalHelper {
	// een operator kan een goal waar maken als een van de effects een instantie van
	// de goal waarmaakt, en geen van de andere effects de goal onwaar maakt.
	
	// (and (heeft Hans ring) (not (heeft Hans kroon))) kan bijvoorbeeld waargemaakt worden met de pak_op actie als
	  /* (:action pak_op
		  	      :parameters     (?personage - personage ?voorwerp - voorwerp ?kamer - kamer)
			      :precondition   (and (in ?personage ?kamer)
			                           (in_vw ?voorwerp ?kamer))
			      :effect         (and (heeft ?personage ?voorwerp)
			                           (not (in_vw ?voorwerp ?kamer)))
			      :agents         (?personage))*/
	// (and
	// (and (in Hans kamer) (in_vw voorwerp ring)) ;; preconditie
	// (not (heeft Hans kroon)) ;; conditie van goal
}
