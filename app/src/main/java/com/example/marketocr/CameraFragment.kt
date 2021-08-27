package com.example.marketocr

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.marketocr.viewModel.ValueViewModel
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer

class CameraFragment : Fragment(), View.OnClickListener{

    // Move this validation for more low level layer
    private val permission = com.example.marketocr.utils.camera.Permission(context)
    private val viewModel: ValueViewModel by viewModels()
    val list = mutableListOf<Int>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater
            .inflate(R
                .layout
                .fragment_camera,
                container,
                false)

        for( i in 0..20){ list.add(i) }

        view?.findViewById<Button>(R.id.button_save)?.setOnClickListener {
            showSaveDialog()
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        activity?.let { permission.requestPermissionCamera(context, it) }
        permission.permissionCheck(context)
        doOcr(view?.findViewById(R.id.surface_camera_preview))
        quantitySetup()

    }

    private fun doOcr(surfaceView: SurfaceView?){

        val ocr = TextRecognizer.Builder(context).build()

        if(!ocr.isOperational){
            Toast.makeText(context, "Baixando dependências, por favor, tente novamente em instantes", Toast.LENGTH_LONG)
            return
        }

        val mCameraSource = CameraSource.Builder(context, ocr)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedPreviewSize(1290, 1024)
            .setAutoFocusEnabled(true)
            .setRequestedFps(2.0f)
            .build()

        surfaceView?.holder?.addCallback(object : SurfaceHolder.Callback{

            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                 try{
                    if(permission.permissionCheck(context) == 0){
                            mCameraSource.start(surfaceView.holder)
                        } else{
                            activity?.let { permission.requestPermissionCamera(context, it) }
                        }
                    } catch (e: Exception) {
                        println(e.message)
                    }
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder) { mCameraSource.stop() }

        })

        ocr.setProcessor(object: Detector.Processor<TextBlock>{
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<TextBlock>?) {

                val items = detections?.detectedItems

                if(items?.size()!! <= 0){
                    return
                }

                val resultTextView = view?.findViewById<TextView>(R.id.tv_result)

                resultTextView?.post {
                    val stringBuilder = StringBuilder()
                    for (i in 0 until items.size()) {
                        val item = items.valueAt(i)
                        stringBuilder.append(item.value)
                        stringBuilder.append("\n")
                    }

                    resultTextView?.text = stringBuilder.toString()

                }
            }
        })
    }

    private fun quantitySetup(){

        val spinner = view?.findViewById<Spinner>(R.id.spinner)
        println("@@@@@@")
        println(list)

        spinner?.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, list)

        spinner?.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                list[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {}

        }
    }


    private fun showSaveDialog(){


        var value = view?.findViewById<TextView>(R.id.tv_result)
        var spinner = view?.findViewById<Spinner>(R.id.spinner)

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Deseja salvar esse valor ?")
        builder.setMessage("Valor: ${value?.text.toString()} | Quantidade: ${list[spinner?.selectedItemPosition!!]}")

        builder
            .setPositiveButton("Sim") { dialog, which ->

                var valueString = value?.text.toString()
                var spinnerInt =list[spinner?.selectedItemPosition!!]

                save(valueString, spinnerInt )
            }
            .setNegativeButton("Não") { dialog, which ->
                Toast.makeText(context, "Valor não salvo", Toast.LENGTH_SHORT)
            }


        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun save(value: String, quantity: Int?){
        viewModel.doInsertValue(value, quantity)
    }

    override fun onClick(v: View?) {}
}


