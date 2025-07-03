package com.example.mulchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class OrderSummaryActivity : AppCompatActivity() {
    //Setting needed variables
    private lateinit var mulchType: String
    private var costPerCubicYard: Double = 0.0
    private var cubicYards: Double = 0.0
    private var deliveryCharge: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)
        //Get intents
        mulchType = intent.getStringExtra("mulchType") ?: ""
        costPerCubicYard = intent.getDoubleExtra("costPerCubicYard", 0.0)
        cubicYards = intent.getDoubleExtra("cubicYards", 0.0)
        deliveryCharge = intent.getDoubleExtra("deliveryCharge", 0.0)

        val street = intent.getStringExtra("street") ?: ""
        val city = intent.getStringExtra("city") ?: ""
        val state = intent.getStringExtra("state") ?: ""
        val zipCode = intent.getStringExtra("zipCode") ?: ""
        val email = intent.getStringExtra("email") ?: ""
        val phone = intent.getStringExtra("phone") ?: ""

        //Delivery statement
        val deliveryAddressSummaryTextView: TextView = findViewById(R.id.deliveryAddressSummaryTextView)
        deliveryAddressSummaryTextView.text = "Delivering $cubicYards cubic yards of $mulchType to:\n \n$street, \n$city, $state \n$zipCode\n \n$email\n $phone"

        val mulchTypeSummaryTextView: TextView = findViewById(R.id.mulchTypeSummaryTextView)
        val cubicYardsSummaryTextView: TextView = findViewById(R.id.cubicYardsSummaryTextView)
        val mulchCostSummaryTextView: TextView = findViewById(R.id.mulchCostSummaryTextView)
        val salesTaxSummaryTextView: TextView = findViewById(R.id.salesTaxSummaryTextView)
        val deliveryChargeSummaryTextView: TextView = findViewById(R.id.deliveryChargeSummaryTextView)
        val totalSummaryTextView: TextView = findViewById(R.id.totalSummaryTextView)

        val backButton: Button = findViewById(R.id.backButton)
        val placeOrderButton: Button = findViewById(R.id.placeOrderButton)

        mulchTypeSummaryTextView.text = "Mulch Type: $mulchType"
        cubicYardsSummaryTextView.text = "Cubic Yards: ${String.format("%.2f", cubicYards)}"
        mulchCostSummaryTextView.text = "Mulch Cost: $${String.format("%.2f", cubicYards * costPerCubicYard)}"
        salesTaxSummaryTextView.text = "Sales Tax: $${String.format("%.2f", 0.07 * cubicYards * costPerCubicYard)}"
        deliveryChargeSummaryTextView.text = "Delivery Charge: $${String.format("%.2f", deliveryCharge)}"

        //Total cost calculation
        val totalCost = cubicYards * costPerCubicYard + 0.07 * cubicYards * costPerCubicYard + deliveryCharge
        totalSummaryTextView.text = "Total: $${String.format("%.2f", totalCost)}"

        backButton.setOnClickListener {
            finish()
        }
        //Show snackbar to notify user that they have placed their order
        placeOrderButton.setOnClickListener {
            showSnackbar("Order Placed!")
        }
    }
    private fun showSnackbar(message: String) {
        val view: View = findViewById(android.R.id.content)
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}