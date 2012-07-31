/**
 * @author PTihomir
 */
$(document).ready(function () {
	AddNewPanel();
	AccountActions();
	generateQuote();
});

function AddNewPanel() {
	$newPanelBG = $('.new-panel-bg');
	$newPanel = $('.new-panel');
	
	$('#create-new-button').click(function(e) {
		e.preventDefault();
		
		$newPanel.toggle();
		$newPanelBG.toggle();
	});
	$newPanel.mouseleave(function() {
		$newPanel.hide();
		$newPanelBG.hide();
	});
}

function AccountActions() {
	$('.personal-tools .account').click(function(e) {
		e.preventDefault();
		
		$('#account').toggle();
	});
}

function generateQuote(){
    setTimeout(function(){
        var $quoteContainer = $('.subheader .description');
        if ($quoteContainer.length) {
            $quoteContainer.fadeOut(1000, function(){
                $quoteContainer.text(QuoteGenerator.getRandomQuote()).fadeIn(1000);
            });
            generateQuote();
        }
    }, 15000);
    
};

var QuoteGenerator = {
	/*quotes are from page: http://www.glensplace.com/WineQuotes2.html*/
	
	quotes: ["Good wine is a good familiar creature if it be well used. ",
	"You need not hang up the ivy branch over the wine that will sell.",
	"Wine is the most civilized thing in the world.",
	"Wine improves with age.  The older I get, the better I like it.",
	"Wine makes daily living easier, less hurried, with fewer tensions and more tolerance.",
	"If your heart is warm with happiness, you'll need a glass - if sorrow chills your heart, have two!",
	"Wine makes every meal an occasion, every table more elegant, every day more civilized.",
	"Making good wine is a skill. Fine wine is an art. ",
	"You haven't drunk too much wine if you can still lie on the floor without holding on.",
	"I cook with wine.  Sometimes I even add it to the food.",
	"If a life of wine, women and song becomes too much, give up the singing.",
	"Hold the bottle up to the light; you will see your dreams are always at the bottom",
	"Wine is the thinking person's health drink.",
	"Consuming wine in moderation daily will help people to die young as late as possible.",
	"Good wine is a necessity of life for me.",
	"Reality is an illusion that occurs due to a lack of wine.",
	"What contemptible scoundrel stole the cork from my lunch?",
	"Wine was created with man in mind, for his pleasure and relaxation, a balm to his good health. ",
	"Artists and poets still find life's meaning in a glass of wine.",
	"Like human beings, a wine's taste is going to depend a great deal on its origins and its upbringing. ",
	"Fresh grapes and wine are perhaps the most luscious foods we mortals encounter during our sojourn here.",
	"Joy is the best of wine.",
	"The best kind of wine is that which is most pleasant to him who drinks it.",
	"From wine what sudden friendship springs.",
	"Wine is the most healthful and most hygienic of beverages.",
	"If God forbade drinking, would He have made wine so good?",
	"It's impossible to separate a great wine from a great experience.",
	"Wine is bottled poetry.",
	"Whenever a man is tired, wine is a great restorer of strength."],
	getRandomQuote: function () {
		var index = Math.floor(this.quotes.length * Math.random());
		return this.quotes[index];
	}
};
