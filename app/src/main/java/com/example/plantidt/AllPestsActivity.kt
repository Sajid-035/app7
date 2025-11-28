package com.example.plantidt

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AllPestsActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var pestRecyclerView: RecyclerView
    private lateinit var backButton: ImageView
    private lateinit var countTextView: TextView

    private lateinit var pestAdapter: PestListAdapter

    private val pestList = listOf(
        // Existing pests
        DiagnosisItem("Root aphids", "root_aphids", R.drawable.ic_aphids),
        DiagnosisItem("Thrips", "thrips", R.drawable.ic_thrips),
        DiagnosisItem("Spider mites", "spider_mites", R.drawable.ic_spider_mites),
        DiagnosisItem("Whiteflies", "whiteflies", R.drawable.ic_whiteflies),
        DiagnosisItem("Scale insects", "scale_insects", R.drawable.ic_scale),
        DiagnosisItem("Mealybugs", "mealybugs", R.drawable.ic_mealybugs),
        DiagnosisItem("Fungus gnats", "fungus_gnats", R.drawable.ic_fungus_gnats),
        DiagnosisItem("Caterpillars", "caterpillars", R.drawable.ic_caterpillars),
        DiagnosisItem("Leaf miners", "leaf_miners", R.drawable.ic_leaf_miners),
        DiagnosisItem("Beetles", "beetles", R.drawable.ic_beetles),
        DiagnosisItem("Slugs and snails", "slugs_snails", R.drawable.ic_slugs),
        DiagnosisItem("Nematodes", "nematodes", R.drawable.ic_nematodes),

        // New pests with ic_disease_default
        DiagnosisItem("Japanese Beetles", "japanese_beetles", R.drawable.ic_disease_default),
        DiagnosisItem("Cutworms", "cutworms", R.drawable.ic_disease_default),
        DiagnosisItem("Armyworms", "armyworms", R.drawable.ic_disease_default),
        DiagnosisItem("Leafrollers", "leafrollers", R.drawable.ic_disease_default),
        DiagnosisItem("Bagworms", "bagworms", R.drawable.ic_disease_default),
        DiagnosisItem("Tent caterpillars", "tent_caterpillars", R.drawable.ic_disease_default),
        DiagnosisItem("Webworms", "webworms", R.drawable.ic_disease_default),
        DiagnosisItem("Sawfly larvae", "sawfly_larvae", R.drawable.ic_disease_default),
        DiagnosisItem("Corn earworm", "corn_earworm", R.drawable.ic_disease_default),
        DiagnosisItem("Tomato hornworm", "tomato_hornworm", R.drawable.ic_disease_default),
        DiagnosisItem("Cabbage worms", "cabbage_worms", R.drawable.ic_disease_default),
        DiagnosisItem("Codling moth", "codling_moth", R.drawable.ic_disease_default),
        DiagnosisItem("Leaf beetles", "leaf_beetles", R.drawable.ic_disease_default),
        DiagnosisItem("Flea beetles", "flea_beetles", R.drawable.ic_disease_default),
        DiagnosisItem("Colorado potato beetle", "colorado_potato_beetle", R.drawable.ic_disease_default),
        DiagnosisItem("Cucumber beetles", "cucumber_beetles", R.drawable.ic_disease_default),
        DiagnosisItem("Squash bugs", "squash_bugs", R.drawable.ic_disease_default),
        DiagnosisItem("Stink bugs", "stink_bugs", R.drawable.ic_disease_default),
        DiagnosisItem("Lace bugs", "lace_bugs", R.drawable.ic_disease_default),
        DiagnosisItem("Chinch bugs", "chinch_bugs", R.drawable.ic_disease_default),
        DiagnosisItem("Bed bugs", "bed_bugs", R.drawable.ic_disease_default),
        DiagnosisItem("Leafhoppers", "leafhoppers", R.drawable.ic_disease_default),
        DiagnosisItem("Planthoppers", "planthoppers", R.drawable.ic_disease_default),
        DiagnosisItem("Psyllids", "psyllids", R.drawable.ic_disease_default),
        DiagnosisItem("Cicadas", "cicadas", R.drawable.ic_disease_default),
        DiagnosisItem("Borers", "borers", R.drawable.ic_disease_default),
        DiagnosisItem("Bark beetles", "bark_beetles", R.drawable.ic_disease_default),
        DiagnosisItem("Weevils", "weevils", R.drawable.ic_disease_default),
        DiagnosisItem("Root weevils", "root_weevils", R.drawable.ic_disease_default),
        DiagnosisItem("Grubs", "grubs", R.drawable.ic_disease_default),
        DiagnosisItem("Wireworms", "wireworms", R.drawable.ic_disease_default),
        DiagnosisItem("Rootworms", "rootworms", R.drawable.ic_disease_default),
        DiagnosisItem("Earwigs", "earwigs", R.drawable.ic_disease_default),
        DiagnosisItem("Millipedes", "millipedes", R.drawable.ic_disease_default),
        DiagnosisItem("Centipedes", "centipedes", R.drawable.ic_disease_default),
        DiagnosisItem("Sowbugs", "sowbugs", R.drawable.ic_disease_default),
        DiagnosisItem("Pillbugs", "pillbugs", R.drawable.ic_disease_default),
        DiagnosisItem("Springtails", "springtails", R.drawable.ic_disease_default),
        DiagnosisItem("Grasshoppers", "grasshoppers", R.drawable.ic_disease_default),
        DiagnosisItem("Crickets", "crickets", R.drawable.ic_disease_default),
        DiagnosisItem("Locusts", "locusts", R.drawable.ic_disease_default),
        DiagnosisItem("Termites", "termites", R.drawable.ic_disease_default),
        DiagnosisItem("Ants", "ants", R.drawable.ic_disease_default),
        DiagnosisItem("Fire ants", "fire_ants", R.drawable.ic_disease_default),
        DiagnosisItem("Carpenter ants", "carpenter_ants", R.drawable.ic_disease_default),
        DiagnosisItem("Wasps", "wasps", R.drawable.ic_disease_default),
        DiagnosisItem("Hornets", "hornets", R.drawable.ic_disease_default),
        DiagnosisItem("Yellow jackets", "yellow_jackets", R.drawable.ic_disease_default),
        DiagnosisItem("Bees", "bees", R.drawable.ic_disease_default),
        DiagnosisItem("Leafcutter bees", "leafcutter_bees", R.drawable.ic_disease_default),
        DiagnosisItem("Carpenter bees", "carpenter_bees", R.drawable.ic_disease_default),
        DiagnosisItem("Bumble bees", "bumble_bees", R.drawable.ic_disease_default),
        DiagnosisItem("Honeybees", "honeybees", R.drawable.ic_disease_default),
        DiagnosisItem("Fruit flies", "fruit_flies", R.drawable.ic_disease_default),
        DiagnosisItem("House flies", "house_flies", R.drawable.ic_disease_default),
        DiagnosisItem("Crane flies", "crane_flies", R.drawable.ic_disease_default),
        DiagnosisItem("Hoverflies", "hoverflies", R.drawable.ic_disease_default),
        DiagnosisItem("Midges", "midges", R.drawable.ic_disease_default),
        DiagnosisItem("Mosquitoes", "mosquitoes", R.drawable.ic_disease_default),
        DiagnosisItem("Gnats", "gnats", R.drawable.ic_disease_default),
        DiagnosisItem("No-see-ums", "no_see_ums", R.drawable.ic_disease_default),
        DiagnosisItem("Black flies", "black_flies", R.drawable.ic_disease_default),
        DiagnosisItem("Horse flies", "horse_flies", R.drawable.ic_disease_default),
        DiagnosisItem("Deer flies", "deer_flies", R.drawable.ic_disease_default),
        DiagnosisItem("Ticks", "ticks", R.drawable.ic_disease_default),
        DiagnosisItem("Mites", "mites", R.drawable.ic_disease_default),
        DiagnosisItem("Chiggers", "chiggers", R.drawable.ic_disease_default),
        DiagnosisItem("Fleas", "fleas", R.drawable.ic_disease_default),
        DiagnosisItem("Lice", "lice", R.drawable.ic_disease_default),
        DiagnosisItem("Booklice", "booklice", R.drawable.ic_disease_default),
        DiagnosisItem("Silverfish", "silverfish", R.drawable.ic_disease_default),
        DiagnosisItem("Firebrats", "firebrats", R.drawable.ic_disease_default),
        DiagnosisItem("Cockroaches", "cockroaches", R.drawable.ic_disease_default),
        DiagnosisItem("Praying mantis", "praying_mantis", R.drawable.ic_disease_default),
        DiagnosisItem("Walking sticks", "walking_sticks", R.drawable.ic_disease_default),
        DiagnosisItem("Lacewings", "lacewings", R.drawable.ic_disease_default),
        DiagnosisItem("Antlions", "antlions", R.drawable.ic_disease_default),
        DiagnosisItem("Dragonflies", "dragonflies", R.drawable.ic_disease_default),
        DiagnosisItem("Damselflies", "damselflies", R.drawable.ic_disease_default),
        DiagnosisItem("Mayflies", "mayflies", R.drawable.ic_disease_default),
        DiagnosisItem("Caddisflies", "caddisflies", R.drawable.ic_disease_default),
        DiagnosisItem("Stoneflies", "stoneflies", R.drawable.ic_disease_default),
        DiagnosisItem("Dobsonflies", "dobsonflies", R.drawable.ic_disease_default),
        DiagnosisItem("Fishflies", "fishflies", R.drawable.ic_disease_default),
        DiagnosisItem("Alderflies", "alderflies", R.drawable.ic_disease_default),
        DiagnosisItem("Scorpionflies", "scorpionflies", R.drawable.ic_disease_default),
        DiagnosisItem("Hangingflies", "hangingflies", R.drawable.ic_disease_default),
        DiagnosisItem("Fleas", "fleas_2", R.drawable.ic_disease_default),
        DiagnosisItem("Strepsipterans", "strepsipterans", R.drawable.ic_disease_default),
        DiagnosisItem("Twisted-wing parasites", "twisted_wing_parasites", R.drawable.ic_disease_default),
        DiagnosisItem("Zorapterans", "zorapterans", R.drawable.ic_disease_default),
        DiagnosisItem("Angel insects", "angel_insects", R.drawable.ic_disease_default),
        DiagnosisItem("Webspinners", "webspinners", R.drawable.ic_disease_default),
        DiagnosisItem("Gladiators", "gladiators", R.drawable.ic_disease_default),
        DiagnosisItem("Rock crawlers", "rock_crawlers", R.drawable.ic_disease_default),
        DiagnosisItem("Ice crawlers", "ice_crawlers", R.drawable.ic_disease_default),
        DiagnosisItem("Heel-walkers", "heel_walkers", R.drawable.ic_disease_default),
        DiagnosisItem("Mantophasmatodeans", "mantophasmatodeans", R.drawable.ic_disease_default)
    )

    private var filteredPestList = pestList.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_pests)

        initializeViews()
        setupRecyclerView()
        setupSearchFunctionality()
        setupClickListeners()
        updateCount()

        // Add entrance animation
        animateEntranceItems()
    }

    private fun initializeViews() {
        searchEditText = findViewById(R.id.searchEditText)
        pestRecyclerView = findViewById(R.id.pestRecyclerView)
        backButton = findViewById(R.id.backButton)
        countTextView = findViewById(R.id.countTextView)
    }

    private fun setupRecyclerView() {
        pestAdapter = PestListAdapter(filteredPestList) { item ->
            openPestDetail(item)
        }
        pestRecyclerView.layoutManager = LinearLayoutManager(this)
        pestRecyclerView.adapter = pestAdapter
    }

    private fun setupSearchFunctionality() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterItems(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterItems(query: String) {
        val lowerQuery = query.lowercase()

        filteredPestList.clear()
        filteredPestList.addAll(
            pestList.filter {
                it.name.lowercase().contains(lowerQuery) ||
                        it.type.lowercase().contains(lowerQuery)
            }
        )

        pestAdapter.notifyDataSetChanged()
        updateCount()
    }

    private fun updateCount() {
        countTextView.text = "${filteredPestList.size} pests found"
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun animateEntranceItems() {
        pestRecyclerView.alpha = 0f
        pestRecyclerView.translationY = 100f

        pestRecyclerView.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(300)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }

    private fun openPestDetail(item: DiagnosisItem) {
        when (item.type) {
            "thrips" -> {
                val intent = Intent(this, ThripsActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "root_aphids" -> {
                val intent = Intent(this, RootAphidsActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            else -> {
                // For other pests, show a toast since their detail activities don't exist yet
                Toast.makeText(this, "Opening details for: ${item.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}