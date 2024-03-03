package com.mercy.tarot;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.mercy.tarot.models.MajorArcana;
// import org.springframework.beans.factory.annotation.Autowired;
// import com.mercy.tarot.models.Card;
// import com.mercy.tarot.models.MinorArcana;

@SpringBootTest(classes = TarotApplicationTests.class)
class TarotApplicationTests {
	// @Autowired
	// private MinorArcana MinorArcana;

	// @Autowired
	// private MajorArcana MajorArcana;

	// @Autowired
	// private Card Card;

	@Test
	void contextLoads(){

	}
	@Test
	void majorArcanaCanBeCreated() {
		var majorArcana = new MajorArcana("Judgment", "Judgement", "XIV", "Judgment card description", "the character in your story is judged harshly", true);
    	assertTrue(majorArcana.title == "Judgement");
	}

}
