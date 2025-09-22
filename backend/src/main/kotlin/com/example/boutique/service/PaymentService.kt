package com.example.boutique.service

import com.example.boutique.domain.Order
import com.mercadopago.MercadoPagoConfig
import com.mercadopago.client.preference.PreferenceBackUrlsRequest
import com.mercadopago.client.preference.PreferenceClient
import com.mercadopago.client.preference.PreferenceItemRequest
import com.mercadopago.client.preference.PreferenceRequest
import com.mercadopago.exceptions.MPException
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class PaymentService {

    @Value("\${mercadopago.access.token}")
    private lateinit var accessToken: String

    @Value("\${app.frontend.url}")
    private lateinit var frontendUrl: String

    @PostConstruct
    fun init() {
        MercadoPagoConfig.setAccessToken(accessToken)
    }

    fun createPreference(order: Order): String? {
        try {
            val items = order.items.map { orderItem ->
                PreferenceItemRequest.builder()
                    .id(orderItem.product.id.toString())
                    .title(orderItem.product.name)
                    .quantity(orderItem.quantity)
                    .unitPrice(orderItem.priceAtPurchase)
                    .build()
            }

            val backUrls = PreferenceBackUrlsRequest.builder()
                .success("$frontendUrl/payment/success")
                .failure("$frontendUrl/payment/failure")
                .pending("$frontendUrl/payment/pending")
                .build()

            val request = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .build()

            val client = PreferenceClient()
            val preference = client.create(request)

            // Return the init_point URL which redirects the user to the checkout
            return preference.initPoint
        } catch (e: MPException) {
            // Handle exceptions from the MercadoPago SDK
            e.printStackTrace()
            return null
        }
    }
}
