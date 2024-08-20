package com.example.jetnewscopy.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.jetnewscopy.R
import com.example.jetnewscopy.databinding.FragmentArticleBinding
import com.example.jetnewscopy.ui.NewsActivity
import com.example.jetnewscopy.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var newsViewModel: NewsViewModel
    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var binding: FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        val article = args.article

        // Log the article URL to ensure it is being passed correctly
        Log.d("ArticleFragment", "Article URL: ${article.url}")

        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            webViewClient = object : WebViewClient() {
                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    Log.e("ArticleFragment", "Failed to load URL: ${article.url}, Error: ${error?.description}")
                    Toast.makeText(context, "Failed to load article", Toast.LENGTH_SHORT).show()
                }

                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    super.onReceivedHttpError(view, request, errorResponse)
                    Log.e("ArticleFragment", "HTTP error: ${errorResponse?.statusCode} ${errorResponse?.reasonPhrase}")
                    Toast.makeText(context, "HTTP error occurred", Toast.LENGTH_SHORT).show()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Log.d("ArticleFragment", "Page loaded: $url")
                }
            }

            article.url?.takeIf { it.isNotEmpty() }?.let {
                loadUrl(it)
            } ?: run {
                Log.e("ArticleFragment", "Article URL is null or empty!")
                Toast.makeText(context, "URL is empty or invalid", Toast.LENGTH_SHORT).show()
            }
        }

        binding.fab.setOnClickListener {
            newsViewModel.addToFavourites(article)
            Snackbar.make(view, "Added to favourites", Snackbar.LENGTH_SHORT).show()
        }
    }
}
