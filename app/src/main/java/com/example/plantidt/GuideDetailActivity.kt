package com.example.plantidt

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class GuideDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_detail)

        val guideType = intent.getStringExtra("GUIDE_TYPE")?.let {
            GuideType.valueOf(it)
        } ?: GuideType.WATERING

        setupUI(guideType)
    }

    private fun setupUI(guideType: GuideType) {
        supportActionBar?.apply {
            title = getGuideTitle(guideType)
            setDisplayHomeAsUpEnabled(true)
        }

        // Load guide content based on type
        loadGuideContent(guideType)
    }

    private fun getGuideTitle(type: GuideType): String {
        return when (type) {
            GuideType.WATERING -> "Watering Guide"
            GuideType.TEMPERATURE -> "Temperature Guide"
            GuideType.SUNLIGHT -> "Sunlight Guide"
            GuideType.SOIL -> "Soil Guide"
            GuideType.FERTILIZING -> "Fertilizing Guide"
            GuideType.REPOTTING -> "Repotting Guide"
            GuideType.PEST -> "Pest Guide"
            GuideType.DISEASE -> "Disease Guide"
            GuideType.HUMIDITY -> "Humidity Guide"
            GuideType.SOWING -> "Sowing Guide"
            GuideType.PRUNING -> "Pruning Guide"
            GuideType.HARVESTING -> "Harvesting Guide"
            GuideType.TILLING -> "Soil Tilling Guide"
        }
    }

    private fun loadGuideContent(type: GuideType) {
        when (type) {
            GuideType.WATERING -> loadWateringGuide()
            GuideType.TEMPERATURE -> loadTemperatureGuide()
            GuideType.SUNLIGHT -> loadSunlightGuide()
            GuideType.SOIL -> loadSoilGuide()
            GuideType.FERTILIZING -> loadFertilizerGuide()
            else -> loadDefaultGuide(type)
        }
    }

    private fun loadFertilizerGuide() {
        // Set main title and description
        findViewById<TextView>(R.id.tvGuideTitle).text = "A Beginner's Guide to Fertilizing Plants"
        findViewById<TextView>(R.id.tvGuideDescription).text =
            "If soil is the plate and water is the drink, then fertilizer is the vitamin supplement for your plants. " +
                    "While potting soil comes with an initial dose of nutrients, plants use them up over time. " +
                    "Fertilizing replenishes these essential minerals, promoting lush leaves, strong roots, and beautiful blooms."

        // Set quick tips
        findViewById<TextView>(R.id.tvQuickTips).text =
            "• Less is more - easier to kill by over-fertilizing than under-fertilizing\n" +
                    "• Fertilize during growing season (Spring & Summer) only\n" +
                    "• Water first, fertilize second - never on dry soil\n" +
                    "• Don't fertilize stressed or newly repotted plants\n" +
                    "• Follow package directions - when in doubt, use less"

        // Add detailed content sections
        addFertilizerContentSections()
    }

    private fun addFertilizerContentSections() {
        val contentContainer = findViewById<androidx.core.widget.NestedScrollView>(R.id.nestedScrollView)
            .getChildAt(0) as android.widget.LinearLayout

        // Section 1: Understanding N-P-K with the provided image
        addContentSectionWithImage(
            contentContainer,
            "Understanding the \"Big Three\": N-P-K",
            "When you look at any fertilizer package, you'll see three numbers, like 10-10-10 or 5-10-5. " +
                    "This is the N-P-K ratio, and it's the most important thing to understand.\n\n" +
                    "🍃 N - NITROGEN:\n" +
                    "Promotes green, leafy growth. (Think: Leaves)\n\n" +
                    "🌸 P - PHOSPHORUS:\n" +
                    "Supports strong roots, flowers, fruits, and seeds. (Think: Roots & Blooms)\n\n" +
                    "🛡️ K - POTASSIUM:\n" +
                    "Boosts overall plant health, disease resistance, and water regulation. (Think: Overall Vigor)\n\n" +
                    "A \"balanced\" fertilizer (like 10-10-10) is a great all-purpose choice. A fertilizer high in " +
                    "Phosphorus (like 5-10-5) is often marketed as a \"bloom booster.\"",
            "#E8F5E8",
            "npk_fertilizer_guide", // This should be your provided N-P-K image
            "Infographic showing N-P-K nutrients: Nitrogen for green leaves, Phosphorus for strong roots & flowers, Potassium for overall health, with a 10-10-10 fertilizer bag"
        )

        // Section 2: Types of Fertilizer
        addContentSection(
            contentContainer,
            "Types of Fertilizer: How to Apply It",
            "There are three main types you'll encounter for houseplants.\n\n" +
                    "🌱 LIQUID FERTILIZER (Most Common):\n" +
                    "What it is: A concentrate that you mix with water according to the package directions.\n" +
                    "How to use: You apply it while watering your plants.\n" +
                    "Pros: Fast-acting, easy to control the strength, absorbed quickly.\n" +
                    "Cons: Needs to be applied regularly (e.g., every 2-4 weeks during the growing season).\n\n" +
                    "⏳ SLOW-RELEASE GRANULES:\n" +
                    "What it is: Small, coated pellets that you mix into the top layer of soil.\n" +
                    "How to use: Each time you water, a small amount of fertilizer is released into the soil.\n" +
                    "Pros: \"Set it and forget it\" for several months. Reduces the risk of fertilizer burn.\n" +
                    "Cons: Harder to control the release; not ideal if a plant suddenly needs a boost.\n\n" +
                    "🥢 FERTILIZER SPIKES OR STAKES:\n" +
                    "What it is: Solid spikes of compressed fertilizer that you push into the soil.\n" +
                    "How to use: Insert into the soil halfway between the plant stem and the edge of the pot.\n" +
                    "Pros: Convenient and mess-free.\n" +
                    "Cons: ⚠️ Use with caution. They can create \"hot spots\" of concentrated fertilizer that can " +
                    "burn nearby roots. Granules and liquids offer more even distribution.",
            "#F0F8FF"
        )

        // Section 3: When to Fertilize
        addContentSection(
            contentContainer,
            "When to Fertilize (And When NOT To)",
            "Timing is everything. Fertilizing at the wrong time can do more harm than good.\n\n" +
                    "✅ FERTILIZE DURING THE GROWING SEASON (Spring & Summer):\n" +
                    "This is when plants are actively putting out new leaves and growing. They are \"hungry\" and " +
                    "can use the extra nutrients.\n" +
                    "General Rule: Fertilize every 2-4 weeks with a diluted liquid fertilizer, or as the package directs.\n\n" +
                    "❌ DO NOT FERTILIZE DURING THE DORMANT SEASON (Fall & Winter):\n" +
                    "Most houseplants slow their growth significantly when the days get shorter and there's less light. " +
                    "They are \"resting\" and feeding them can lead to weak growth and fertilizer burn.",
            "#FFF8E1"
        )

        // Section 4: Golden Rules
        addContentSection(
            contentContainer,
            "The Golden Rules of Fertilizing",
            "Follow these rules to avoid common mistakes.\n\n" +
                    "📏 LESS IS MORE:\n" +
                    "It is far easier to kill a plant by over-fertilizing than by under-fertilizing. If you're unsure, " +
                    "it's always safer to use less.\n\n" +
                    "📖 READ THE DIRECTIONS:\n" +
                    "Every fertilizer is different. Always follow the dilution and application instructions on the label. " +
                    "A popular method is \"weakly, weekly\"—using a ¼ strength solution once a week instead of a full " +
                    "dose once a month.\n\n" +
                    "💧 WATER FIRST, FERTILIZE SECOND:\n" +
                    "Never apply fertilizer to dry soil. This can scorch the roots. Water your plant as you normally " +
                    "would, then apply the diluted fertilizer. This ensures the roots can absorb it safely.\n\n" +
                    "🚫 DON'T FERTILIZE A STRESSED PLANT:\n" +
                    "If your plant is sick, has pests, is severely wilted, or has just been repotted, give it time to " +
                    "recover before fertilizing. It's like trying to force-feed someone when they're unwell.",
            "#F3E5F5"
        )

        // Section 5: Signs of Trouble
        addContentSection(
            contentContainer,
            "Signs of Trouble",
            "Learn to recognize the warning signs of fertilizer problems.\n\n" +
                    "😔 UNDER-FERTILIZED:\n" +
                    "• Pale or yellowing lower leaves\n" +
                    "• Very slow or no growth during the growing season\n" +
                    "• Weak stems\n\n" +
                    "🔥 OVER-FERTILIZED (Fertilizer Burn):\n" +
                    "• Crispy, brown leaf tips and edges\n" +
                    "• A white, crusty salt-like buildup on the surface of the soil\n" +
                    "• Sudden leaf drop\n" +
                    "• Stunted growth\n\n" +
                    "If you notice signs of over-fertilization, stop fertilizing immediately and flush the soil with " +
                    "plain water to remove excess salts. Remove any damaged leaves and allow the plant to recover.",
            "#FFE5E5"
        )

        // Section 6: Success Tips
        addContentSection(
            contentContainer,
            "Quick Tips for Fertilizing Success",
            "Follow these essential guidelines for healthy, thriving plants:\n\n" +
                    "🧪 START WEAK:\n" +
                    "Begin with half the recommended strength and observe how your plant responds. You can always " +
                    "increase later.\n\n" +
                    "🔄 CONSISTENCY MATTERS:\n" +
                    "Regular, light feeding is better than occasional heavy doses. Stick to a schedule during the " +
                    "growing season.\n\n" +
                    "🌱 MATCH THE PLANT'S NEEDS:\n" +
                    "Flowering plants benefit from higher phosphorus, while foliage plants prefer more nitrogen. " +
                    "Cacti and succulents need very little fertilizer.\n\n" +
                    "🚿 FLUSH PERIODICALLY:\n" +
                    "Once a month, water thoroughly until water runs out the drainage holes. This prevents salt " +
                    "buildup from fertilizers.\n\n" +
                    "📱 KEEP RECORDS:\n" +
                    "Note when you fertilize each plant. This helps you maintain a consistent schedule and track " +
                    "your plants' responses.",
            "#E3F2FD"
        )
    }

    private fun loadSoilGuide() {
        // Set main title and description
        findViewById<TextView>(R.id.tvGuideTitle).text = "Complete Soil Guide"
        findViewById<TextView>(R.id.tvGuideDescription).text =
            "Understanding soil is just as important as understanding sunlight and water. The right soil mix is the foundation of a healthy plant, providing three critical things: " +
                    "anchorage for roots, essential nutrients for growth, and the perfect balance of water retention and drainage."

        // Set quick tips
        findViewById<TextView>(R.id.tvQuickTips).text =
            "• Never use garden soil - it's too dense for containers\n" +
                    "• Always use pots with drainage holes\n" +
                    "• Don't pack soil down - keep it loose and airy\n" +
                    "• Refresh top inch of soil annually for nutrients\n" +
                    "• Choose soil mix based on your plant type"

        // Add detailed content sections
        addSoilContentSections()
    }

    private fun addSoilContentSections() {
        val contentContainer = findViewById<androidx.core.widget.NestedScrollView>(R.id.nestedScrollView)
            .getChildAt(0) as android.widget.LinearLayout

        // Section 1: Understanding Soil Basics with the provided image
        addContentSectionWithImage(
            contentContainer,
            "What Good Potting Mix Provides",
            "The right soil mix is the foundation of a healthy plant, providing three critical things:\n\n" +
                    "🏗️ ANCHORAGE:\n" +
                    "It gives roots a structure to hold onto, keeping the plant stable and upright.\n\n" +
                    "🍽️ NUTRIENTS:\n" +
                    "It holds and delivers the essential minerals and food the plant needs to grow strong and healthy.\n\n" +
                    "💧 WATER & AIR BALANCE:\n" +
                    "It retains enough moisture for the roots to drink while also allowing for air pockets, so the roots can breathe and don't rot. Garden soil is too dense for this!\n\n" +
                    "A good potting mix is not just dirt; it's a carefully balanced recipe of different ingredients, each with a specific job.",
            "#E8F5E8",
            "ideal_potting_mix", // This should be your provided image
            "Infographic showing the four components of ideal potting mix: Base (Coco Coir/Peat Moss), Aeration (Perlite/Pumice), Moisture Retention (Vermiculite), and Nutrition (Compost/Worm Castings)"
        )

        // Section 2: The Building Blocks
        addContentSection(
            contentContainer,
            "The Building Blocks of a Great Potting Mix",
            "Almost all quality potting soils are made from a combination of these ingredients. Understanding them helps you choose the right bag or even mix your own!\n\n" +
                    "🌱 THE BASE (The \"Fluff\"):\n" +
                    "This makes up the bulk of the mix. The two most common bases are:\n" +
                    "• Peat Moss: Decomposed sphagnum moss. Excellent at holding water and nutrients but can be difficult to re-wet once completely dry. Less sustainable resource.\n" +
                    "• Coco Coir: Made from coconut husks. Holds water well but allows great aeration. Easier to re-wet than peat and more eco-friendly.\n\n" +
                    "💨 AERATION & DRAINAGE (The \"Breathing Room\"):\n" +
                    "These lightweight amendments create air pockets, preventing compaction and root rot:\n" +
                    "• Perlite: Little white bits that look like Styrofoam. Super-light volcanic glass excellent for drainage and aeration.\n" +
                    "• Pumice: Porous volcanic rock, heavier than perlite. Adds aeration without floating to the top.\n" +
                    "• Orchid Bark: Pieces of fir bark for very chunky, airy mixes.\n\n" +
                    "🧽 MOISTURE RETENTION (The \"Sponges\"):\n" +
                    "These help soil hold water for longer periods:\n" +
                    "• Vermiculite: Shiny, golden flakes that absorb water and nutrients, releasing them slowly.\n" +
                    "• Sphagnum Moss: Long-fibered moss that can hold up to 20 times its weight in water.\n\n" +
                    "🍄 NUTRITION (The \"Food\"):\n" +
                    "Organic components that add vital nutrients:\n" +
                    "• Compost: Decomposed organic matter rich in nutrients and beneficial microbes.\n" +
                    "• Worm Castings: Nutrient-rich, gentle soil amendment that improves soil structure.",
            "#F0F8FF"
        )

        // Section 3: Soil Mix Recipes
        addContentSection(
            contentContainer,
            "Common Potting Mix Recipes for Different Plants",
            "You don't need to be a scientist! You can buy pre-made bags or follow these simple \"recipes.\" Think of \"parts\" as any unit of measurement (a scoop, a cup, a bucket).\n\n" +
                    "🌿 STANDARD ALL-PURPOSE HOUSEPLANT MIX:\n" +
                    "Good for: Pothos, Spider Plants, Philodendrons, Dracaena\n" +
                    "Recipe: 2 parts Coco Coir/Peat Moss + 1 part Perlite/Pumice + handful of Worm Castings\n" +
                    "Goal: Balanced moisture retention and good drainage\n\n" +
                    "🌵 SUCCULENT & CACTUS MIX:\n" +
                    "Good for: All Cacti, Echeveria, Sempervivum, Aloe\n" +
                    "Recipe: 1 part Potting Soil + 2 parts Perlite/Pumice + 1 part coarse sand\n" +
                    "Goal: Very fast drainage to prevent root rot. Soil should dry quickly\n\n" +
                    "🍃 AROID MIX (Chunky & Airy):\n" +
                    "Good for: Monstera, Anthurium, Hoyas, Orchids\n" +
                    "Recipe: 1 part Coco Coir + 1 part Perlite/Pumice + 1 part Orchid Bark + handful of Worm Castings\n" +
                    "Goal: Maximum airflow to roots, mimicking their natural tree-climbing environment",
            "#FFF8E1"
        )

        // Section 4: Signs Your Plant Needs Fresh Soil
        addContentSection(
            contentContainer,
            "Signs Your Plant Needs Fresh Soil (Repotting)",
            "Watch for these indicators that it's time to refresh your plant's soil:\n\n" +
                    "🌱 PHYSICAL SIGNS:\n" +
                    "• Roots growing out of drainage holes\n" +
                    "• Plant is \"root-bound\" (roots are a dense, tangled mass)\n" +
                    "• Soil looks compacted, dry, and pulling away from pot sides\n\n" +
                    "💧 WATERING ISSUES:\n" +
                    "• Water runs straight through without seeming to soak in\n" +
                    "• Soil takes forever to dry out (sign of breakdown)\n" +
                    "• Water pools on top instead of absorbing\n\n" +
                    "📈 GROWTH PROBLEMS:\n" +
                    "• Growth has slowed or stopped during growing season\n" +
                    "• Leaves yellowing despite proper care\n" +
                    "• Plant seems generally unhealthy despite good conditions\n\n" +
                    "⏰ TIME-BASED:\n" +
                    "• Most houseplants benefit from fresh soil every 1-2 years\n" +
                    "• Fast-growing plants may need annual soil refresh",
            "#F3E5F5"
        )

        // Section 5: Soil Success Tips
        addContentSection(
            contentContainer,
            "Quick Tips for Soil Success",
            "Follow these essential guidelines for the best results:\n\n" +
                    "🚫 NEVER USE GARDEN SOIL:\n" +
                    "Soil from your yard is too heavy, contains pests, and doesn't drain properly in containers. It will compact and suffocate your plant's roots.\n\n" +
                    "🕳️ ALWAYS USE DRAINAGE HOLES:\n" +
                    "This is non-negotiable. Drainage holes are the #1 way to prevent root rot. No exceptions!\n\n" +
                    "🤲 DON'T PACK IT DOWN:\n" +
                    "When repotting, gently fill soil around roots. Don't press down hard - this removes crucial air pockets that roots need to breathe.\n\n" +
                    "🔄 FRESHEN UP REGULARLY:\n" +
                    "If a plant doesn't need full repotting, scrape off the top inch of old soil and replace with fresh, nutrient-rich soil or compost.\n\n" +
                    "🧪 TEST YOUR MIX:\n" +
                    "Good potting mix should feel light and fluffy, not heavy or muddy. When squeezed, it should crumble apart, not form a tight ball.\n\n" +
                    "💰 QUALITY MATTERS:\n" +
                    "Invest in good potting mix - it's the foundation of your plant's health. Cheap mixes often contain too much bark or poor-quality ingredients.",
            "#E3F2FD"
        )
    }

    private fun loadSunlightGuide() {
        // Set main title and description
        findViewById<TextView>(R.id.tvGuideTitle).text = "Sunlight Guide"
        findViewById<TextView>(R.id.tvGuideDescription).text =
            "Sunlight is the food for your plants. Through photosynthesis, plants use light energy to convert water and carbon dioxide into sugars (their energy) and oxygen. " +
                    "The right amount of light is crucial for a plant to be healthy, grow new leaves, and produce flowers."

        // Set quick tips
        findViewById<TextView>(R.id.tvQuickTips).text =
            "• Rotate plants 90 degrees every time you water for even growth\n" +
                    "• Clean leaves with a damp cloth every few weeks to remove dust\n" +
                    "• Acclimate plants slowly when moving to brighter spots\n" +
                    "• Use the hand shadow test to assess light levels\n" +
                    "• Consider grow lights for low-light homes with sun-hungry plants"

        // Add detailed content sections
        addSunlightContentSections()
    }

    private fun addSunlightContentSections() {
        val contentContainer = findViewById<androidx.core.widget.NestedScrollView>(R.id.nestedScrollView)
            .getChildAt(0) as android.widget.LinearLayout

        // Section 1: Visual Guide to Indoor Light Levels
        addContentSectionWithImage(
            contentContainer,
            "Visual Guide to Indoor Light Levels",
            "This illustration shows the different light zones within a typical room. The intensity of light decreases dramatically the farther you move from the window.\n\n" +
                    "Understanding these zones will help you choose the perfect spot for each of your plants based on their specific light requirements.",
            "#FFF8E1",
            "indoor_light_zones", // This would be your provided image
            "Illustration of a room showing different light zones: Direct Light (bright yellow) at the window, Bright Indirect Light (light orange) nearby, Medium Light (pale green) in the middle, and Low Light (blue-grey) in the far corner"
        )

        // Section 2: The Four Main Types of Sunlight
        addContentSection(
            contentContainer,
            "The Four Main Types of Sunlight",
            "Most plant care labels use one of these four terms. Here's what they mean in practical terms:\n\n" +
                    "☀️ FULL SUN (Direct Light):\n" +
                    "• 6+ hours of direct, unfiltered sunlight\n" +
                    "• Found directly in South/West-facing windows\n" +
                    "• Best for: Succulents, Cacti, Herbs, Bird of Paradise\n\n" +
                    "🌤️ BRIGHT, INDIRECT LIGHT:\n" +
                    "• Very bright for 6+ hours, but no direct sun rays\n" +
                    "• A few feet from bright windows or East-facing windows\n" +
                    "• Best for: Monstera, Fiddle Leaf Fig, Pothos, Philodendrons\n\n" +
                    "🌥️ MEDIUM LIGHT:\n" +
                    "• Some indirect light, not brightly lit all day\n" +
                    "• Middle of bright rooms or North-facing windows\n" +
                    "• Best for: Snake Plant, ZZ Plant, Spider Plant, Ferns\n\n" +
                    "☁️ LOW LIGHT:\n" +
                    "• Far from windows, bright enough to read briefly\n" +
                    "• Corners, hallways, or obstructed window areas\n" +
                    "• Best for: ZZ Plant, Snake Plant, Pothos, Cast Iron Plant",
            "#E8F5E8"
        )

        // Section 3: Hand Shadow Test
        addContentSection(
            contentContainer,
            "How to Assess Your Light: The Hand Shadow Test",
            "This is the easiest way to determine what kind of light you have at different times of day:\n\n" +
                    "1. Go to the spot where you want to place your plant\n" +
                    "2. Hold your hand about one foot above the spot\n" +
                    "3. Look at the shadow your hand casts:\n\n" +
                    "🔆 Sharp, clearly defined shadow = Full Sun/Direct Light\n" +
                    "🌤️ Soft, but clearly visible shadow = Bright, Indirect Light\n" +
                    "🌥️ Faint, blurry shadow = Medium Light\n" +
                    "☁️ No real shadow at all = Low Light\n\n" +
                    "Test this at different times throughout the day to get a complete picture of your light conditions.",
            "#F0F8FF"
        )

        // Section 4: Signs of Incorrect Lighting
        addContentSectionWithComparisonImages(
            contentContainer,
            "Signs of Incorrect Lighting",
            "Your plant will tell you if it's unhappy with its lighting conditions.\n\n" +
                    "🌑 Signs of TOO LITTLE LIGHT:\n" +
                    "• Leggy growth with long, stretched stems\n" +
                    "• Small, pale new leaves\n" +
                    "• Loss of variegation (colorful leaves turn green)\n" +
                    "• No new growth or stagnant appearance\n" +
                    "• Plant leaning toward light source\n\n" +
                    "☀️ Signs of TOO MUCH LIGHT:\n" +
                    "• Scorched white, yellow, or brown patches\n" +
                    "• Crispy, brown leaf edges\n" +
                    "• Faded, washed-out leaf color\n" +
                    "• Wilting during the day despite moist soil\n" +
                    "• Leaves curling downward to reduce sun exposure",
            "#FFF8E1",
            "low_light_stressed_plant",
            "high_light_stressed_plant"
        )

        // Section 5: Light Requirements by Plant Type
        addContentSection(
            contentContainer,
            "Light Requirements by Popular Plant Types",
            "🌿 TROPICAL HOUSEPLANTS:\n" +
                    "• Monstera, Philodendron, Pothos: Bright, indirect light\n" +
                    "• Calathea, Maranta: Medium to bright indirect light\n" +
                    "• Fiddle Leaf Fig: Bright, indirect light (6+ hours)\n\n" +
                    "🌵 SUCCULENTS & CACTI:\n" +
                    "• Most varieties: Full sun to bright indirect light\n" +
                    "• Echeveria, Sedum: Direct morning sun preferred\n" +
                    "• Haworthia, Gasteria: Bright indirect light\n\n" +
                    "🌱 LOW-LIGHT CHAMPIONS:\n" +
                    "• ZZ Plant: Thrives in low to medium light\n" +
                    "• Snake Plant: Tolerates very low light\n" +
                    "• Pothos: Adapts to low light (less variegation)\n" +
                    "• Cast Iron Plant: Extremely low light tolerant\n\n" +
                    "🌸 FLOWERING PLANTS:\n" +
                    "• Most need bright indirect to direct light to bloom\n" +
                    "• Orchids: Bright, indirect light\n" +
                    "• African Violets: Bright, indirect light",
            "#F3E5F5"
        )

        // Section 6: Seasonal Light Considerations
        addContentSection(
            contentContainer,
            "Seasonal Light Management",
            "🌸 SPRING:\n" +
                    "• Light intensity increases - watch for sun stress\n" +
                    "• Perfect time to move plants to brighter spots\n" +
                    "• Begin rotating plants more frequently\n\n" +
                    "☀️ SUMMER:\n" +
                    "• Strongest light - protect from harsh afternoon sun\n" +
                    "• Use sheer curtains to filter intense light\n" +
                    "• Move plants back from hot, sunny windows\n\n" +
                    "🍂 FALL:\n" +
                    "• Light begins to decrease - move plants closer to windows\n" +
                    "• Prepare for slower growth with less light\n" +
                    "• Good time to invest in grow lights\n\n" +
                    "❄️ WINTER:\n" +
                    "• Lowest light levels of the year\n" +
                    "• Move plants to brightest available spots\n" +
                    "• Consider supplemental grow lights for tropical plants\n" +
                    "• Clean windows to maximize available light",
            "#E3F2FD"
        )

        // Section 7: Grow Light Basics
        addContentSection(
            contentContainer,
            "Grow Light Basics for Beginners",
            "If your home doesn't get enough natural light, grow lights can be a game-changer.\n\n" +
                    "🔍 WHAT TO LOOK FOR:\n" +
                    "• Full-spectrum LED lights (most energy efficient)\n" +
                    "• Adjustable brightness and timer functions\n" +
                    "• Lights that can be positioned 6-12 inches from plants\n\n" +
                    "⏰ TIMING:\n" +
                    "• Most houseplants: 12-14 hours of grow light daily\n" +
                    "• Flowering plants: 14-16 hours during growing season\n" +
                    "• Low-light plants: 8-10 hours supplemental light\n\n" +
                    "💡 PLACEMENT TIPS:\n" +
                    "• Position lights 6-12 inches above plant tops\n" +
                    "• Adjust height as plants grow\n" +
                    "• Use timers to maintain consistent light schedules\n" +
                    "• Start with shorter periods and gradually increase",
            "#E8F5E8"
        )
    }


    private fun loadTemperatureGuide() {
        // Set main title and description
        findViewById<TextView>(R.id.tvGuideTitle).text = "Temperature Guide"
        findViewById<TextView>(R.id.tvGuideDescription).text =
            "Temperature is one of the most important, yet often overlooked, factors in plant health. " +
                    "Just like us, plants have a preferred temperature range where they feel their best. " +
                    "This guide will help you understand and manage the thermal world of your plants."

        // Set quick tips
        findViewById<TextView>(R.id.tvQuickTips).text =
            "• Ideal range for most houseplants: 65°F to 80°F (18°C to 27°C)\n" +
                    "• Avoid placing plants near heat sources or cold drafts\n" +
                    "• Move plants away from windows during extreme weather\n" +
                    "• Group plants together to help regulate temperature\n" +
                    "• Monitor for signs of temperature stress regularly"

        // Add detailed content sections
        addTemperatureContentSections()
    }

    private fun addTemperatureContentSections() {
        val contentContainer = findViewById<androidx.core.widget.NestedScrollView>(R.id.nestedScrollView)
            .getChildAt(0) as android.widget.LinearLayout

        // Section 1: The Sweet Spot (using Image 2 - fern with thermostat)
        addContentSectionWithImage(
            contentContainer,
            "The \"Sweet Spot\": Ideal Temperature Range",
            "For the vast majority of common tropical and subtropical houseplants (like Monsteras, Pothos, Philodendrons, and Fiddle Leaf Figs), the ideal temperature range is one they share with us.\n\n" +
                    "🌡️ Ideal Range: 65°F to 80°F (18°C to 27°C)\n\n" +
                    "If you are comfortable in a room, your plants probably are too. The most important thing is stability - plants dislike sudden, dramatic temperature swings.\n\n" +
                    "Temperature directly affects your plant's core processes:\n" +
                    "• Photosynthesis: Converting light into energy\n" +
                    "• Respiration: How plants \"breathe\" and use stored energy\n" +
                    "• Transpiration: Water evaporation that cools the plant",
            "#E8F5E8",
            "temperature_thermostat_fern", // Image 2
            "A healthy fern next to a thermostat showing the ideal temperature of 72°F"
        )

        // Section 2: Reading Temperature Stress Signs
        addContentSectionWithComparisonImages(
            contentContainer,
            "Reading the Signs: Temperature Stress",
            "🔥 Signs of HEAT STRESS (Too Hot):\n" +
                    "• Wilting or drooping leaves despite adequate water\n" +
                    "• Scorched or burnt leaf tips and edges\n" +
                    "• Sudden leaf drop to conserve water\n" +
                    "• Stunted growth as plant enters survival mode\n" +
                    "• Rapid soil drying requiring frequent watering\n\n" +
                    "❄️ Signs of COLD STRESS (Too Cold):\n" +
                    "• Wilting due to damaged cells preventing water uptake\n" +
                    "• Leaf discoloration - dark, black, or translucent leaves\n" +
                    "• Sudden dropping of healthy-looking leaves\n" +
                    "• Mushy spots from freeze-thaw cell damage\n" +
                    "• Severely stunted growth in cold conditions",
            "#FFF8E1",
            "heat_stressed_plant",
            "cold_stressed_plant"
        )

        // Section 3: Temperature Management (using Image 1 - plants near radiator)
        addContentSectionWithImage(
            contentContainer,
            "How to Manage Temperature for Your Plants",
            "Being proactive is the key to preventing temperature-related issues.\n\n" +
                    "🛡️ Protecting from COLD:\n" +
                    "• Move plants away from cold windows in winter\n" +
                    "• Avoid drafty areas like doors and AC vents\n" +
                    "• Use cardboard barriers between plants and cold glass\n" +
                    "• Bring outdoor container plants inside before frost\n\n" +
                    "🌡️ Protecting from HEAT:\n" +
                    "• Shield from intense afternoon sun with sheer curtains\n" +
                    "• Keep away from heaters, radiators, and heat vents\n" +
                    "• Ensure air circulation with gentle fans\n" +
                    "• Group plants to raise local humidity and moderate heat",
            "#F0F8FF",
            "plants_near_radiator", // Image 1
            "A collection of plants positioned safely away from a radiator, demonstrating proper temperature management"
        )

        // Section 4: Different Plant Types
        addContentSection(
            contentContainer,
            "Temperature Needs for Different Plant Types",
            "🌿 TROPICAL PLANTS (Monstera, Calathea, Ferns):\n" +
                    "• Hate cold and drafts - keep consistently 65-80°F (18-27°C)\n" +
                    "• Most sensitive to temperature fluctuations\n\n" +
                    "🌵 SUCCULENTS & CACTI (Echeveria, Aloe):\n" +
                    "• Tolerate higher heat during the day\n" +
                    "• Prefer cooler nights: 55-60°F (13-16°C)\n" +
                    "• Protect from frost at all costs\n\n" +
                    "🌸 FLOWERING PLANTS (Orchids, Christmas Cactus):\n" +
                    "• Many need cooler nighttime temperatures to bloom\n" +
                    "• A 10-15°F (5-8°C) night drop can trigger flowering\n\n" +
                    "🌱 OUTDOOR PLANTS (Vegetables, Perennials):\n" +
                    "• Follow USDA Hardiness Zone guidelines\n" +
                    "• Require \"hardening off\" before outdoor planting",
            "#F3E5F5"
        )

        // Section 5: Seasonal Temperature Tips
        addContentSection(
            contentContainer,
            "Seasonal Temperature Management",
            "🌸 SPRING:\n" +
                    "• Gradually introduce plants to warmer conditions\n" +
                    "• Watch for late cold snaps that can damage tender growth\n\n" +
                    "☀️ SUMMER:\n" +
                    "• Monitor for overheating in sunny locations\n" +
                    "• Increase humidity to help plants cope with heat\n\n" +
                    "🍂 FALL:\n" +
                    "• Prepare plants for cooler indoor conditions\n" +
                    "• Reduce watering as growth slows in cooler temperatures\n\n" +
                    "❄️ WINTER:\n" +
                    "• Keep plants away from cold windows and heating vents\n" +
                    "• Maintain consistent indoor temperatures\n" +
                    "• Consider using plant heat mats for sensitive tropical plants",
            "#E3F2FD"
        )
    }

    private fun loadWateringGuide() {
        // Set main title and description
        findViewById<TextView>(R.id.tvGuideTitle).text = "Complete Watering Guide"
        findViewById<TextView>(R.id.tvGuideDescription).text =
            "Improper watering is the single most common reason plants fail, but it doesn't have to be complicated. " +
                    "Forget rigid schedules and learn to \"listen\" to your plants. This guide will teach you everything you need to know about giving your plants the perfect drink, every time."

        // Set quick tips
        findViewById<TextView>(R.id.tvQuickTips).text =
            "• Check soil moisture with the finger test before watering\n" +
                    "• Water until it drains from the bottom holes\n" +
                    "• Empty saucers after watering to prevent root rot\n" +
                    "• Use lukewarm water, never cold or hot\n" +
                    "• It's better to underwater slightly than overwater"

        // Add detailed content sections
        addWateringContentSections()
    }

    private fun addWateringContentSections() {
        val contentContainer = findViewById<androidx.core.widget.NestedScrollView>(R.id.nestedScrollView)
            .getChildAt(0) as android.widget.LinearLayout

        // Section 1: The Golden Rule
        addContentSectionWithImage(
            contentContainer,
            "The #1 Golden Rule: Check Before You Water",
            "The most important skill is learning when your plant actually needs water. Watering on a strict schedule (e.g., \"every Sunday\") is a recipe for disaster because a plant's needs change daily.\n\n" +
                    "The best method is the Finger Test:\n" +
                    "• Gently insert your index finger into the soil up to your second knuckle (about 1-2 inches deep)\n" +
                    "• If the soil feels dry at your fingertip, it's time to water\n" +
                    "• If you feel any moisture, wait another day or two and check again\n" +
                    "• It's almost always better to underwater slightly than to overwater",
            "#E8F5E8",
            "finger_test_demo",
            "A clear, close-up photo showing a person's finger inserted about two inches into the soil of a potted plant, demonstrating the Finger Test."
        )

        // Section 2: Watering Techniques
        addContentSectionWithDualImages(
            contentContainer,
            "How to Water: The Right Techniques",
            "Once you've determined your plant is thirsty, how you water is just as important.\n\n" +
                    "🔹 Top Watering (The Classic Method):\n" +
                    "1. Use lukewarm water to avoid shocking the roots\n" +
                    "2. Pour water slowly and evenly over the soil surface\n" +
                    "3. Avoid splashing leaves to prevent fungal diseases\n" +
                    "4. Continue until water flows from drainage holes\n" +
                    "5. Empty the saucer after a few minutes\n\n" +
                    "🔹 Bottom Watering (The Soaking Method):\n" +
                    "1. Place pot in a basin with a few inches of lukewarm water\n" +
                    "2. Let the plant absorb water for 15-30 minutes\n" +
                    "3. Remove when topsoil becomes damp\n" +
                    "4. Great for plants sensitive to wet leaves",
            "#F0F8FF",
            "top_watering_demo",
            "bottom_watering_demo"
        )

        // Section 3: Reading the Signs
        addContentSectionWithComparisonImages(
            contentContainer,
            "Reading the Signs: Thirsty or Drowning?",
            "🚨 Signs of UNDERWATERING (Thirsty Plant):\n" +
                    "• Wilting or drooping leaves that perk up after watering\n" +
                    "• Soil pulling away from pot sides\n" +
                    "• Dry, crispy, brown leaf edges\n" +
                    "• Slow or stunted growth\n" +
                    "• Lower leaves turning yellow and dropping\n\n" +
                    "⚠️ Signs of OVERWATERING (Drowning Plant):\n" +
                    "• Wilting, limp leaves even when soil is wet\n" +
                    "• Yellowing leaves, starting with lower ones\n" +
                    "• Mushy, soft, or blackening stems at the base\n" +
                    "• Brown or black, mushy roots (root rot)\n" +
                    "• Sour or swampy smell from soil\n" +
                    "• Fungus gnats hovering around soil",
            "#FFF8E1",
            "underwatered_plant",
            "overwatered_plant"
        )

        // Section 4: Factors Affecting Watering
        addContentSection(
            contentContainer,
            "Factors That Affect How Often You Water",
            "🌱 Plant Type: A fern needs more water than a succulent\n\n" +
                    "🏺 Pot Material: \n" +
                    "• Terracotta pots dry out quickly\n" +
                    "• Plastic/glazed ceramic retain moisture longer\n\n" +
                    "☀️ Light & Temperature: Plants in bright, warm spots use water faster\n\n" +
                    "🗓️ Season: \n" +
                    "• More water during growing season (spring/summer)\n" +
                    "• Less water during dormant period (fall/winter)\n\n" +
                    "📏 Pot Size: Small pots dry out much faster than large pots",
            "#F3E5F5"
        )
    }

    private fun addContentSectionWithImage(
        container: android.widget.LinearLayout,
        title: String,
        content: String,
        backgroundColor: String = "#FFFFFF",
        imageName: String,
        imageDescription: String,
        maxImageHeight: Int = 600
    ) {
        val cardView = androidx.cardview.widget.CardView(this).apply {
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 32)
            }
            radius = 16f
            cardElevation = 8f

            try {
                val color = android.graphics.Color.parseColor(backgroundColor)
                setCardBackgroundColor(color)
            } catch (e: IllegalArgumentException) {
                setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
            }
        }

        val linearLayout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val titleTextView = TextView(this).apply {
            text = title
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(context, android.R.color.black))
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 16)
            }
        }

        // Create image with actual dimensions
        val imageView = android.widget.ImageView(this).apply {
            val resourceId = resources.getIdentifier(imageName, "drawable", packageName)
            if (resourceId != 0) {
                val drawable = ContextCompat.getDrawable(context, resourceId)
                drawable?.let { d ->
                    val actualWidth = d.intrinsicWidth
                    val actualHeight = d.intrinsicHeight

                    // Convert maxImageHeight from dp to pixels
                    val maxHeightPx = (maxImageHeight * resources.displayMetrics.density).toInt()

                    // Calculate scaled dimensions maintaining aspect ratio
                    val scaledHeight = if (actualHeight > maxHeightPx) {
                        maxHeightPx
                    } else {
                        actualHeight
                    }

                    layoutParams = android.widget.LinearLayout.LayoutParams(
                        android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                        scaledHeight
                    ).apply {
                        setMargins(0, 0, 0, 16)
                    }

                    scaleType = android.widget.ImageView.ScaleType.CENTER_CROP
                    adjustViewBounds = true
                    setImageDrawable(d)
                } ?: run {
                    // Fallback if drawable is null
                    setDefaultImageLayout()
                }
            } else {
                setDefaultImageLayout()
            }

            contentDescription = imageDescription
        }

        val contentTextView = TextView(this).apply {
            text = content
            textSize = 14f
            setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            setLineSpacing(4f, 1f)
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        linearLayout.addView(titleTextView)
        linearLayout.addView(imageView)
        linearLayout.addView(contentTextView)
        cardView.addView(linearLayout)
        container.addView(cardView)
    }

    private fun addContentSectionWithDualImages(
        container: android.widget.LinearLayout,
        title: String,
        content: String,
        backgroundColor: String = "#FFFFFF",
        leftImageName: String,
        rightImageName: String,
        maxImageHeight: Int = 400
    ) {
        val cardView = androidx.cardview.widget.CardView(this).apply {
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 32)
            }
            radius = 16f
            cardElevation = 8f

            try {
                val color = android.graphics.Color.parseColor(backgroundColor)
                setCardBackgroundColor(color)
            } catch (e: IllegalArgumentException) {
                setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
            }
        }

        val linearLayout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val titleTextView = TextView(this).apply {
            text = title
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(context, android.R.color.black))
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 16)
            }
        }

        // Create horizontal layout for dual images
        val imageContainer = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.HORIZONTAL
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 16)
            }
        }

        // Helper function to create image with actual dimensions for dual layout
        fun createDualImageView(imageName: String, isLeft: Boolean): android.widget.ImageView {
            return android.widget.ImageView(this).apply {
                val resourceId = resources.getIdentifier(imageName, "drawable", packageName)
                if (resourceId != 0) {
                    val drawable = ContextCompat.getDrawable(context, resourceId)
                    drawable?.let { d ->
                        val actualHeight = d.intrinsicHeight
                        val maxHeightPx = (maxImageHeight * resources.displayMetrics.density).toInt()

                        val finalHeight = if (actualHeight > maxHeightPx) maxHeightPx else actualHeight

                        layoutParams = android.widget.LinearLayout.LayoutParams(
                            0,
                            finalHeight,
                            1f
                        ).apply {
                            if (isLeft) {
                                setMargins(0, 0, 8, 0)
                            } else {
                                setMargins(8, 0, 0, 0)
                            }
                        }

                        scaleType = android.widget.ImageView.ScaleType.CENTER_CROP
                        adjustViewBounds = true
                        setImageDrawable(d)
                    } ?: run {
                        setDefaultDualImageLayout(isLeft)
                    }
                } else {
                    setDefaultDualImageLayout(isLeft)
                }

                contentDescription = if (isLeft) "Left image: $imageName" else "Right image: $imageName"
            }
        }

        val leftImageView = createDualImageView(leftImageName, true)
        val rightImageView = createDualImageView(rightImageName, false)

        imageContainer.addView(leftImageView)
        imageContainer.addView(rightImageView)

        // Add labels under images
        val labelContainer = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.HORIZONTAL
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 16)
            }
        }

        val leftLabel = TextView(this).apply {
            text = "Top Watering"
            textSize = 12f
            setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            gravity = android.view.Gravity.CENTER
            layoutParams = android.widget.LinearLayout.LayoutParams(0, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val rightLabel = TextView(this).apply {
            text = "Bottom Watering"
            textSize = 12f
            setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            gravity = android.view.Gravity.CENTER
            layoutParams = android.widget.LinearLayout.LayoutParams(0, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        labelContainer.addView(leftLabel)
        labelContainer.addView(rightLabel)

        val contentTextView = TextView(this).apply {
            text = content
            textSize = 14f
            setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            setLineSpacing(4f, 1f)
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        linearLayout.addView(titleTextView)
        linearLayout.addView(imageContainer)
        linearLayout.addView(labelContainer)
        linearLayout.addView(contentTextView)
        cardView.addView(linearLayout)
        container.addView(cardView)
    }

    private fun addContentSectionWithComparisonImages(
        container: android.widget.LinearLayout,
        title: String,
        content: String,
        backgroundColor: String = "#FFFFFF",
        leftImageName: String,
        rightImageName: String,
        maxImageHeight: Int = 400
    ) {
        val cardView = androidx.cardview.widget.CardView(this).apply {
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 32)
            }
            radius = 16f
            cardElevation = 8f

            try {
                val color = android.graphics.Color.parseColor(backgroundColor)
                setCardBackgroundColor(color)
            } catch (e: IllegalArgumentException) {
                setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
            }
        }

        val linearLayout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val titleTextView = TextView(this).apply {
            text = title
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(context, android.R.color.black))
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 16)
            }
        }

        // Create horizontal layout for comparison images
        val imageContainer = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.HORIZONTAL
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 16)
            }
        }

        // Helper function to create comparison image with actual dimensions
        fun createComparisonImageView(imageName: String, isLeft: Boolean): android.widget.ImageView {
            return android.widget.ImageView(this).apply {
                val resourceId = resources.getIdentifier(imageName, "drawable", packageName)
                if (resourceId != 0) {
                    val drawable = ContextCompat.getDrawable(context, resourceId)
                    drawable?.let { d ->
                        val actualHeight = d.intrinsicHeight
                        val maxHeightPx = (maxImageHeight * resources.displayMetrics.density).toInt()

                        val finalHeight = if (actualHeight > maxHeightPx) maxHeightPx else actualHeight

                        layoutParams = android.widget.LinearLayout.LayoutParams(
                            0,
                            finalHeight,
                            1f
                        ).apply {
                            if (isLeft) {
                                setMargins(0, 0, 8, 0)
                            } else {
                                setMargins(8, 0, 0, 0)
                            }
                        }

                        scaleType = android.widget.ImageView.ScaleType.CENTER_CROP
                        adjustViewBounds = true
                        setImageDrawable(d)
                    } ?: run {
                        setDefaultDualImageLayout(isLeft)
                    }
                } else {
                    setDefaultDualImageLayout(isLeft)
                }

                contentDescription = if (isLeft) "Underwatered plant example" else "Overwatered plant example"
            }
        }

        val leftImageView = createComparisonImageView(leftImageName, true)
        val rightImageView = createComparisonImageView(rightImageName, false)

        imageContainer.addView(leftImageView)
        imageContainer.addView(rightImageView)

        // Add labels under images
        val labelContainer = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.HORIZONTAL
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 16)
            }
        }

        val leftLabel = TextView(this).apply {
            text = "Underwatered"
            textSize = 12f
            setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            gravity = android.view.Gravity.CENTER
            layoutParams = android.widget.LinearLayout.LayoutParams(0, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val rightLabel = TextView(this).apply {
            text = "Overwatered"
            textSize = 12f
            setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            gravity = android.view.Gravity.CENTER
            layoutParams = android.widget.LinearLayout.LayoutParams(0, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        labelContainer.addView(leftLabel)
        labelContainer.addView(rightLabel)

        val contentTextView = TextView(this).apply {
            text = content
            textSize = 14f
            setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            setLineSpacing(4f, 1f)
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        linearLayout.addView(titleTextView)
        linearLayout.addView(imageContainer)
        linearLayout.addView(labelContainer)
        linearLayout.addView(contentTextView)
        cardView.addView(linearLayout)
        container.addView(cardView)
    }

    private fun addContentSection(
        container: android.widget.LinearLayout,
        title: String,
        content: String,
        backgroundColor: String = "#FFFFFF"
    ) {
        val cardView = androidx.cardview.widget.CardView(this).apply {
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 32)
            }
            radius = 16f
            cardElevation = 8f

            try {
                val color = android.graphics.Color.parseColor(backgroundColor)
                setCardBackgroundColor(color)
            } catch (e: IllegalArgumentException) {
                setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
            }
        }

        val linearLayout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val titleTextView = TextView(this).apply {
            text = title
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(context, android.R.color.black))
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 16)
            }
        }

        val contentTextView = TextView(this).apply {
            text = content
            textSize = 14f
            setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            setLineSpacing(4f, 1f)
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        linearLayout.addView(titleTextView)
        linearLayout.addView(contentTextView)
        cardView.addView(linearLayout)
        container.addView(cardView)
    }

    // Extension function for ImageView to set default layout
    private fun android.widget.ImageView.setDefaultImageLayout() {
        layoutParams = android.widget.LinearLayout.LayoutParams(
            android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
            400
        ).apply {
            setMargins(0, 0, 0, 16)
        }
        setImageResource(android.R.drawable.ic_menu_gallery)
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray))
    }

    // Extension function for default dual image layout
    private fun android.widget.ImageView.setDefaultDualImageLayout(isLeft: Boolean) {
        layoutParams = android.widget.LinearLayout.LayoutParams(
            0,
            300,
            1f
        ).apply {
            if (isLeft) {
                setMargins(0, 0, 8, 0)
            } else {
                setMargins(8, 0, 0, 0)
            }
        }
        setImageResource(android.R.drawable.ic_menu_gallery)
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray))
    }

    private fun loadDefaultGuide(type: GuideType) {
        findViewById<TextView>(R.id.tvGuideTitle).text = getGuideTitle(type)
        findViewById<TextView>(R.id.tvGuideDescription).text = "Content for ${getGuideTitle(type)} coming soon..."
        findViewById<TextView>(R.id.tvQuickTips).text = "Quick tips will be available soon for this guide."
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}