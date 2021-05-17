//package API;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class Request_response {
//
//
//    public JSONObject getResponse(context parents, String url){
//        RequestQueue queue = Volley.newRequestQueue(parents);
//
//
//    }
//    RequestQueue queue = Volley.newRequestQueue();
//
//    RequestQueue queue = Volley.newRequestQueue(this);
//    String url ="https://www.json-generator.com/api/json/get/bUpUgkQvsi?indent=2";
//
//    // Request a string response from the provided URL.
//    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//            new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    // Display the first 500 characters of the response string.
//                    response = response.substring(1, response.length() - 1);
//                    JSONObject object = new JSONObject();
//                    try {
//                        object = new JSONObject(response);
//                        System.out.println(object.get("departureDate"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
////                        res_my_text.setText("Response is: "+ response);
//                }
//            }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            res_my_text.setText("That didn't work!");
//
//        }
//    });
//
//// Add the request to the RequestQueue.
//        queue.add(stringRequest);
//
//
//}
