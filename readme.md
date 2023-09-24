OpenMap {
val gmmIntentUri = Uri.parse("google.navigation:q=20.3550659, 85.8204287&mode=w")
val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
mapIntent.setPackage("com.google.android.apps.maps")
startActivity(mapIntent)
}