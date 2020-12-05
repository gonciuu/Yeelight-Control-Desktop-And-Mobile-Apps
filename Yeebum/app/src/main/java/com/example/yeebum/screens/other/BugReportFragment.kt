package com.example.yeebum.screens.other

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yeebum.R
import com.example.yeebum.screens.components.Helpers
import com.example.yeebum.screens.components.LoadingDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.DateTime
import kotlinx.android.synthetic.main.fragment_bug_report.*
import java.util.*


class BugReportFragment : Fragment() {


    private val helpers = Helpers()
    private val firebaseDB = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_bug_report, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bugReportBackButton.setOnClickListener { requireActivity().onBackPressed() }
        sendBugReportButton.setOnClickListener {sendBugReport()}
    }


    //----------------------------| Send Bug Report to Firestore |---------------------------
    private fun sendBugReport() {
        val dialog = LoadingDialog.getDialog(requireContext(), "Sending...")
        dialog.show()
        val bug = hashMapOf("text" to bugTextEntry.text.toString().trim())
        try{
            firebaseDB.collection("bug_reports").add(bug)
                .addOnSuccessListener {
                    dialog.dismiss()
                    helpers.closeKeyboard(requireActivity())
                    helpers.showSnackBar(requireView(), "Bug report sent", null, null)
                    requireActivity().onBackPressed()
                }.addOnFailureListener{
                    dialog.dismiss()
                    helpers.showSnackBar(requireView(), it.message.toString(), null, null)
                }
        }catch (ex:Exception){
            dialog.dismiss()
            helpers.showSnackBar(requireView(), ex.message.toString(), null, null)
        }
    }
    //=====================================================================================

}