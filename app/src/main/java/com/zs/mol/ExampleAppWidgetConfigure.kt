package com.zs.mol

import com.zs.mol.ExampleAppWidgetConfigure

class ExampleAppWidgetConfigure : Activity() {
    var mAppWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID
    var mAppWidgetPrefix: EditText? = null
    fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        // Set the result to CANCELED.  This will cause the widget host to cancel
// out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED)
        // Set the view layout resource to use.
        setContentView(R.layout.appwidget_configure)
        // Find the EditText
        mAppWidgetPrefix = findViewById(R.id.appwidget_prefix) as EditText?
        // Bind the action for the save button.
        findViewById(R.id.save_button).setOnClickListener(mOnClickListener)
        // Find the widget id from the intent.
        val intent: Intent = getIntent()
        val extras: Bundle = intent.getExtras()
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }
        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
        }
        mAppWidgetPrefix.setText(
            loadTitlePref(
                this@ExampleAppWidgetConfigure,
                mAppWidgetId
            )
        )
    }

    var mOnClickListener: View.OnClickListener = object : OnClickListener() {
        fun onClick(v: View?) {
            val context: Context = this@ExampleAppWidgetConfigure
            // When the button is clicked, save the string in our prefs and return that they
// clicked OK.
            val titlePrefix: String = mAppWidgetPrefix.getText().toString()
            saveTitlePref(context, mAppWidgetId, titlePrefix)
            // Push widget update to surface with newly set prefix
            val appWidgetManager: AppWidgetManager = AppWidgetManager.getInstance(context)
            ExampleAppWidgetProvider.updateAppWidget(
                context, appWidgetManager,
                mAppWidgetId, titlePrefix
            )
            // Make sure we pass back the original appWidgetId
            val resultValue = Intent()
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
            setResult(RESULT_OK, resultValue)
            finish()
        }
    }

    companion object {
        const val TAG = "ExampleAppWidgetConfigure"
        private const val PREFS_NAME =
            "com.example.android.apis.appwidget.ExampleAppWidgetProvider"
        private const val PREF_PREFIX_KEY = "prefix_"
        // Write the prefix to the SharedPreferences object for this widget
        fun saveTitlePref(context: Context, appWidgetId: Int, text: String?) {
            val prefs: SharedPreferences.Editor =
                context.getSharedPreferences(PREFS_NAME, 0)
                    .edit()
            prefs.putString(PREF_PREFIX_KEY + appWidgetId, text)
            prefs.commit()
        }

        // Read the prefix from the SharedPreferences object for this widget.
// If there is no preference saved, get the default from a resource
        fun loadTitlePref(context: Context, appWidgetId: Int): String {
            val prefs: SharedPreferences =
                context.getSharedPreferences(PREFS_NAME, 0)
            val prefix: String = prefs.getString(
                PREF_PREFIX_KEY + appWidgetId,
                null
            )
            return prefix ?: context.getString(R.string.appwidget_prefix_default)
        }

        fun deleteTitlePref(context: Context?, appWidgetId: Int) {}
        fun loadAllTitlePrefs(
            context: Context?, appWidgetIds: ArrayList<Int?>?,
            texts: ArrayList<String?>?
        ) {
        }
    }
}