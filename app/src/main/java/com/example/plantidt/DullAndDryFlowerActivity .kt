package com.example.plantidt

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView

class DullAndDryFlowerActivity : AppCompatActivity() {

    // Data class to hold dull and dry flower scenario information
    data class DullDryFlowerScenario(
        val title: String,
        val description: String,
        val imageRes: Int,
        val causes: List<Cause>,
        val solutions: List<Solution>
    )

    data class Cause(
        val title: String,
        val description: String
    )

    data class Solution(
        val title: String,
        val action: String,
        val whyItWorks: String,
        val extraTip: String? = null
    )

    private val dullDryFlowerScenarios = listOf(
        DullDryFlowerScenario(
            "Cut Flowers in a Vase",
            "Addresses a fresh bouquet that quickly becomes limp, wilted, and dull-colored after being placed in a vase.",
            R.drawable.cut_flowers_vase, // You'll need to add this image
            listOf(
                Cause(
                    "Dehydration due to Air Embolism",
                    "When stems are cut in the open air, a tiny air bubble can get sucked into the stem's water transport system (the xylem). This bubble acts like a blockage, preventing water from reaching the flower head, causing it to wilt and dry out, even while sitting in water."
                ),
                Cause(
                    "Bacterial Clogging",
                    "The water in a vase quickly becomes a breeding ground for bacteria. This bacteria, along with decaying leaves below the waterline, creates a slimy \"biofilm\" that clogs the cut end of the stems, physically blocking water absorption."
                ),
                Cause(
                    "Lack of Nutrition",
                    "Cut flowers are separated from their parent plant, their source of sugar (energy). Without this energy, they can't maintain their cellular processes, leading to premature aging and a dull appearance."
                ),
                Cause(
                    "Exposure to Ethylene Gas",
                    "Ethylene is an invisible, odorless gas that acts as an aging hormone for plants. It's released by ripening fruit (especially bananas and apples), decaying plant matter, and even cigarette smoke. Exposure drastically speeds up the wilting and petal-dropping process."
                ),
                Cause(
                    "Improper Environment",
                    "Placing flowers in direct sunlight, near a heat source (radiator, electronics), or in a draft causes them to lose moisture through their petals (transpiration) faster than they can absorb it through their stems."
                )
            ),
            listOf(
                Solution(
                    "Proper Stem Preparation",
                    "As soon as you get your flowers, use a sharp, clean knife or shears to recut 1-2 inches off each stem at a 45-degree angle. The most critical step is to do this under running water or in a bowl of water.",
                    "Cutting underwater prevents an air bubble (embolism) from forming. The angled cut increases the surface area for water absorption.",
                    "Also, remove any leaves that will sit below the waterline in the vase. This prevents them from rotting and creating bacteria."
                ),
                Solution(
                    "Use a Clean Vase and Flower Food",
                    "Always start with a vase that has been scrubbed clean with soap and hot water. Fill it with cool, fresh water and mix in the flower food packet that came with your bouquet. If you don't have a packet, you can make your own: 1 quart of water + 1 teaspoon of sugar (food) + 2-3 drops of bleach (to kill bacteria).",
                    "The flower food provides essential sugar for energy, an acidifier to help the stems absorb water, and a biocide to keep the water clean and free of stem-clogging bacteria."
                ),
                Solution(
                    "Daily Maintenance",
                    "Every day, or at least every other day, change the water completely. Don't just top it off. Give the vase a quick rinse and add fresh water and more flower food. Recutting the stems a little bit every 2-3 days is also highly effective.",
                    "This removes the bacteria that has started to grow and gives the flowers a fresh, unclogged surface to drink from."
                ),
                Solution(
                    "Strategic Placement",
                    "Place your flower arrangement in a cool location, away from direct sunlight and heat sources. Most importantly, keep it far away from your fruit bowl.",
                    "This minimizes moisture loss and avoids exposure to the aging ethylene gas from ripening fruit."
                ),
                Solution(
                    "Emergency Revival Technique",
                    "For badly wilted flowers, try a \"shock treatment.\" Recut the stems and submerge the entire flower—stem, leaves, and head—in a sink or tub of cool water for 30-60 minutes.",
                    "This allows the flower to absorb water through its petals and leaves, rapidly rehydrating it from all surfaces."
                )
            )
        ),
        DullDryFlowerScenario(
            "Flowers on a Living Plant",
            "Addresses blooms on a living plant (indoor or outdoor) that appear dull, crispy at the edges, small, or fail to open properly.",
            R.drawable.living_plant_flowers, // You'll need to add this image
            listOf(
                Cause(
                    "Improper Watering",
                    "Underwatering: The plant doesn't have enough water to support its blooms, which are often the first part of the plant to show stress.\n\nOverwatering: This leads to root rot. Damaged, rotten roots cannot absorb water and nutrients, so even though the soil is wet, the plant is effectively \"drying out\" from a functional standpoint."
                ),
                Cause(
                    "Nutrient Deficiency",
                    "Flowering requires immense energy. A lack of Phosphorus (P) and Potassium (K)—nutrients essential for bloom development—will result in weak, small, and poorly colored flowers."
                ),
                Cause(
                    "Incorrect Sunlight",
                    "Too much direct sun can scorch and dry out delicate petals. Not enough sun means the plant lacks the energy to produce large, vibrant blooms."
                ),
                Cause(
                    "Pests and Disease",
                    "Pests like thrips, aphids, and spider mites suck the life out of buds and flowers, causing discoloration and distortion. Fungal diseases like powdery mildew can coat flowers, making them look dull and unhealthy."
                ),
                Cause(
                    "Need for Deadheading",
                    "When a flower fades, the plant's energy shifts to producing seeds in that spot. This diverts energy away from creating new, fresh blooms."
                )
            ),
            listOf(
                Solution(
                    "Correct Your Watering",
                    "Check the soil before you water. Stick your finger 1-2 inches deep. If it's dry, water thoroughly until it runs out of the drainage holes. If it's still damp, wait. Never let a plant sit in a saucer of standing water.",
                    "This prevents both underwatering and the root rot caused by overwatering, ensuring the roots are healthy and functional."
                ),
                Solution(
                    "Fertilize for Blooms",
                    "During the growing season, use a fertilizer labeled as a \"bloom booster\" or one with a higher middle number (P) and last number (K) in the N-P-K ratio (e.g., 10-30-20).",
                    "This provides the specific nutrients your plant needs to produce and sustain large, colorful, and healthy flowers."
                ),
                Solution(
                    "Deadhead Spent Flowers",
                    "As soon as a flower starts to fade and look dry, pinch or snip it off at the base of its little stem. This is called deadheading.",
                    "This tricks the plant into thinking it hasn't successfully reproduced, encouraging it to redirect its energy into producing more new flowers instead of seeds."
                )
            )
        ),
        DullDryFlowerScenario(
            "Preserved or Dried Flowers",
            "Addresses a dried flower arrangement that has become dull, faded, dusty, and overly brittle over time.",
            R.drawable.preserved_dried_flowers, // You'll need to add this image
            listOf(
                Cause(
                    "Sunlight (UV Degradation)",
                    "Direct sunlight is the primary cause of fading. UV rays break down the pigments in dried petals, turning vibrant colors into dull shades of tan and brown."
                ),
                Cause(
                    "Dust Accumulation",
                    "A fine layer of household dust will settle on the arrangement, obscuring the colors and making it look old and drab."
                ),
                Cause(
                    "Humidity and Brittleness",
                    "High humidity can cause dried flowers to reabsorb moisture, becoming limp and even moldy. Conversely, an overly dry environment can make them extremely brittle and prone to shattering."
                )
            ),
            listOf(
                Solution(
                    "Prevent Fading",
                    "Display your dried flower arrangements in a location that does not receive any direct sunlight.",
                    "This is the single most effective way to preserve the original colors for years."
                ),
                Solution(
                    "Gentle Dusting",
                    "To remove dust, use a very gentle method. A can of compressed air (for electronics) held at a distance works well. Alternatively, use a hairdryer on its coolest, lowest-power setting. For delicate areas, a very soft makeup brush or artist's paintbrush is perfect.",
                    "These methods remove the dulling layer of dust without damaging the fragile petals."
                ),
                Solution(
                    "Add a Protective Sealant",
                    "For added protection against humidity and to reduce shedding, you can give the arrangement a very light misting with a commercial dried flower sealant spray. In a pinch, a light coat of unscented aerosol hairspray can also work.",
                    "The sealant creates a thin, protective barrier that helps lock in color and fortify the fragile structure of the flowers."
                )
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dull_dry_flower)

        setupBackButton()
        setupDullDryFlowerScenarioCards()
    }

    private fun setupBackButton() {
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupDullDryFlowerScenarioCards() {
        val containerLayout = findViewById<LinearLayout>(R.id.dullDryFlowerScenariosContainer)

        dullDryFlowerScenarios.forEach { scenario ->
            val cardView = createDullDryFlowerCard(scenario)
            containerLayout.addView(cardView)
        }
    }

    private fun createDullDryFlowerCard(scenario: DullDryFlowerScenario): MaterialCardView {
        val cardView = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(32, 16, 32, 16)
            }
            cardElevation = 8f
            radius = 16f
            setCardBackgroundColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, android.R.color.white))
        }

        val contentLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
        }

        // Header with image and title
        val headerLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 0, 0, 16)
        }

        val imageView = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(80, 80).apply {
                setMargins(0, 0, 16, 0)
            }
            setImageResource(scenario.imageRes)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }

        val titleLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
            orientation = LinearLayout.VERTICAL
        }

        val titleTextView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = scenario.title
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, android.R.color.black))
        }

        val descriptionTextView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = scenario.description
            textSize = 14f
            setTextColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, android.R.color.darker_gray))
            setPadding(0, 8, 0, 0)
        }

        titleLayout.addView(titleTextView)
        titleLayout.addView(descriptionTextView)
        headerLayout.addView(imageView)
        headerLayout.addView(titleLayout)

        // Content container (initially hidden)
        val contentContainer = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
            visibility = View.GONE
        }

        // Add causes section
        val causesTitle = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = "Primary Causes"
            textSize = 16f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, R.color.warning_orange))
            setPadding(0, 16, 0, 8)
        }
        contentContainer.addView(causesTitle)

        scenario.causes.forEach { cause ->
            val causeCard = createCauseCard(cause)
            contentContainer.addView(causeCard)
        }

        // Add solutions section
        val solutionsTitle = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = "Solutions"
            textSize = 16f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, R.color.solution_blue))
            setPadding(0, 16, 0, 8)
        }
        contentContainer.addView(solutionsTitle)

        scenario.solutions.forEach { solution ->
            val solutionCard = createSolutionCard(solution)
            contentContainer.addView(solutionCard)
        }

        contentLayout.addView(headerLayout)
        contentLayout.addView(contentContainer)
        cardView.addView(contentLayout)

        // Click listener to expand/collapse
        cardView.setOnClickListener {
            if (contentContainer.visibility == View.GONE) {
                contentContainer.visibility = View.VISIBLE
                cardView.cardElevation = 12f
            } else {
                contentContainer.visibility = View.GONE
                cardView.cardElevation = 8f
            }
        }

        return cardView
    }

    private fun createCauseCard(cause: Cause): MaterialCardView {
        val causeCard = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 4, 0, 4)
            }
            cardElevation = 4f
            radius = 12f
            setCardBackgroundColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, android.R.color.background_light))
        }

        val causeLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
        }

        val causeTitle = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = cause.title
            textSize = 14f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, R.color.warning_orange))
            setPadding(0, 0, 0, 8)
        }

        val causeDescription = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = cause.description
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, android.R.color.black))
        }

        causeLayout.addView(causeTitle)
        causeLayout.addView(causeDescription)
        causeCard.addView(causeLayout)
        return causeCard
    }

    private fun createSolutionCard(solution: Solution): MaterialCardView {
        val solutionCard = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 4, 0, 4)
            }
            cardElevation = 4f
            radius = 12f
            setCardBackgroundColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, android.R.color.background_light))
        }

        val solutionLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
        }

        val solutionTitle = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = solution.title
            textSize = 14f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, R.color.solution_blue))
            setPadding(0, 0, 0, 8)
        }

        val actionTitle = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = "Action:"
            textSize = 13f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, R.color.plant_green))
            setPadding(0, 0, 0, 4)
        }

        val actionText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = solution.action
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, android.R.color.black))
            setPadding(0, 0, 0, 8)
        }

        val whyTitle = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = "Why it works:"
            textSize = 13f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, R.color.plant_green))
            setPadding(0, 0, 0, 4)
        }

        val whyText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = solution.whyItWorks
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, android.R.color.black))
        }

        solutionLayout.addView(solutionTitle)
        solutionLayout.addView(actionTitle)
        solutionLayout.addView(actionText)
        solutionLayout.addView(whyTitle)
        solutionLayout.addView(whyText)

        // Add extra tip if available
        if (solution.extraTip != null) {
            val extraTipTitle = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = "Extra Tip:"
                textSize = 13f
                setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, R.color.plant_green))
                setPadding(0, 8, 0, 4)
            }

            val extraTipText = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = solution.extraTip
                textSize = 13f
                setTextColor(ContextCompat.getColor(this@DullAndDryFlowerActivity, android.R.color.black))
            }

            solutionLayout.addView(extraTipTitle)
            solutionLayout.addView(extraTipText)
        }

        solutionCard.addView(solutionLayout)
        return solutionCard
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}