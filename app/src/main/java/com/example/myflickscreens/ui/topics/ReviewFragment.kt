package com.example.myflickscreens.ui.topics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myflickscreens.R
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue

class ReviewFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReviewAdapter
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.top_reviews, container, false)
        recyclerView = view.findViewById(R.id.top_reviews_list)
        recyclerView.layoutManager = LinearLayoutManager(context)

        loadReviews()

        return view
    }

    private fun loadReviews() {
        firestore.collection("users").get()
            .addOnSuccessListener { userSnapshot ->
                val users = userSnapshot.documents.mapNotNull { userDoc ->
                    userDoc.id to userDoc.toObject(User::class.java)
                }.toMap()

                val allReviews = mutableListOf<Pair<UserReview, User>>()

                val tasks = users.map { (userId, user) ->
                    firestore.collection("users").document(userId).collection("reviews").get()
                        .addOnSuccessListener { reviewSnapshot ->
                            val userReviews = reviewSnapshot.documents.mapNotNull { reviewDoc ->
                                reviewDoc.toObject(UserReview::class.java)?.let { review ->
                                    if (user != null) review to user else null
                                }
                            }
                            allReviews.addAll(userReviews)
                        }
                }

                Tasks.whenAllComplete(tasks).addOnSuccessListener {
                    // Passa as reviews ao adaptador
                    adapter = ReviewAdapter(
                        allReviews,
                        onLikeClick = ::handleLike,
                        onDislikeClick = ::handleDislike
                    )
                    recyclerView.adapter = adapter
                }.addOnFailureListener { exception ->
                    Log.e("ReviewFragment", "Erro ao carregar reviews", exception)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ReviewFragment", "Erro ao carregar usuários", exception)
            }
    }


    private fun handleLike(reviewId: String) {
        firestore.collectionGroup("reviews")
            .whereEqualTo("reviewId", reviewId)
            .get()
            .addOnSuccessListener { snapshot ->
                snapshot.documents.forEach { document ->
                    document.reference.update("likes", FieldValue.increment(1))
                }
            }
    }

    private fun handleDislike(reviewId: String) {
        firestore.collectionGroup("reviews")
            .whereEqualTo("reviewId", reviewId)
            .get()
            .addOnSuccessListener { snapshot ->
                snapshot.documents.forEach { document ->
                    document.reference.update("dislikes", FieldValue.increment(1))
                }
            }
    }
}
