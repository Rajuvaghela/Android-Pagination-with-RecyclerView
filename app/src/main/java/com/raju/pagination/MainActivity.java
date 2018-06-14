package com.raju.pagination;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raju.pagination.api.MovieApi;
import com.raju.pagination.api.MovieService;
import com.raju.pagination.models.AllProductResponse;
import com.raju.pagination.models.ProductDetailBean;
import com.raju.pagination.utils.PaginationScrollListener;
import com.suleiman.pagination.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    PaginationAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    RecyclerView rv;
    ProgressBar progressBar;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;

    private MovieService movieService;
    RequestQueue queue;
    Activity activity;
    ArrayList<AllProductResponse> allProductResponses=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        queue = Volley.newRequestQueue(this);

        rv = (RecyclerView) findViewById(R.id.main_recycler);
        progressBar = (ProgressBar) findViewById(R.id.main_progress);

        adapter = new PaginationAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);

        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setAdapter(adapter);

        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        //init service and load data
        movieService = MovieApi.getClient().create(MovieService.class);

        loadFirstPage();

    }

/*    private void loadFirstPage() {
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("country", "pk");
        postParam.put("state", "PB");
        postParam.put("postcode", "");
        postParam.put("city", "");
        postParam.put("paged", "2");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "https://www.awanmart.com.pk/?webservice=1&vootouchservice=get_products", new JSONObject(postParam),
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("res2", "" + response.toString());


                        try {
                            JSONObject object = new JSONObject(response.toString());

                            if (object.has("Calender_Event_People")) {
                                JSONArray jsonArray = object.getJSONArray("Calender_Event_People");
                                if (jsonArray.length() != 0) {
                                    allProductResponses.clear();
                                    allProductResponses.addAll((Collection<? extends AllProductResponse>) new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<AllProductResponse>>() {
                                    }.getType()));
                                }
                            }
                           // Log.e("page",""+allProductResponses.get(0).Page);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


          *//*              List<Result> results = fetchResults(response);
                        progressBar.setVisibility(View.GONE);
                        adapter.addAll(results);

                        if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                        else isLastPage = true;*//*
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error2", "" + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        jsonObjReq.setTag(MainActivity.this);
        queue.add(jsonObjReq);

    }*/

    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");

        callTopRatedMoviesApi().enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                // Got data. Send it to adapter

                List<ProductDetailBean> results = fetchResults(response);
                progressBar.setVisibility(View.GONE);
                adapter.addAll(results);

                if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                t.printStackTrace();
                // TODO: 08/11/16 handle failure
            }
        });

    }


    private List<ProductDetailBean> fetchResults(Response<AllProductResponse> response) {
        AllProductResponse topRatedMovies = response.body();
        return topRatedMovies.products;
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);

        callTopRatedMoviesApi().enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                adapter.removeLoadingFooter();
                isLoading = false;

                List<ProductDetailBean> results = fetchResults(response);
                adapter.addAll(results);

                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                t.printStackTrace();
                // TODO: 08/11/16 handle failure
            }
        });
    }


    /**
     * Performs a Retrofit call to the top rated movies API.
     * Same API call for Pagination.
     * As {@link #currentPage} will be incremented automatically
     * by @{@link PaginationScrollListener} to load next page.
     */
    private Call<AllProductResponse> callTopRatedMoviesApi() {
        return movieService.getTopRatedMovies(
                getString(R.string.my_api_key),
                "en_US",
                currentPage
        );
    }


}
