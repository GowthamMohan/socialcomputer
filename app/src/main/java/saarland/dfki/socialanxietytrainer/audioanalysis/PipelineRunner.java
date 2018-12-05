/*
 * PipelineRunner.java
 * Copyright (c) 2018
 * Authors: Ionut Damian, Michael Dietz, Frank Gaibler, Daniel Langerenken, Simon Flutura,
 * Vitalijs Krumins, Antonio Grieco
 * *****************************************************
 * This file is part of the Social Signal Interpretation for Java (SSJ) framework
 * developed at the Lab for Human Centered Multimedia of the University of Augsburg.
 *
 * SSJ has been inspired by the SSI (http://openssi.net) framework. SSJ is not a
 * one-to-one port of SSI to Java, it is an approximation. Nor does SSJ pretend
 * to offer SSI's comprehensive functionality and performance (this is java after all).
 * Nevertheless, SSJ borrows a lot of programming patterns from SSI.
 *
 * This library is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this library; if not, see <http://www.gnu.org/licenses/>.
 */

package saarland.dfki.socialanxietytrainer.audioanalysis;

import android.util.Log;

import com.jjoe64.graphview.GraphView;

import hcm.ssj.audio.AudioChannel;
import hcm.ssj.audio.Microphone;
import hcm.ssj.audio.Pitch;
import hcm.ssj.core.ExceptionHandler;
import hcm.ssj.core.Pipeline;
import hcm.ssj.core.Provider;
import hcm.ssj.graphic.SignalPainter;

public class PipelineRunner extends Thread {

    private boolean _terminate = false;
    private Pipeline _ssj;

    private IPipeLine _act = null;
    private GraphView _graphs[] = null;

    public PipelineRunner(IPipeLine a, GraphView[] graphs) {
        _act = a;
        _graphs = graphs;

        if (Pipeline.isInstanced())
            Pipeline.getInstance().clear();
        _ssj = Pipeline.getInstance();
    }

    public void setExceptionHandler(ExceptionHandler h) {
        if (_ssj == null)
            return;

        _ssj.setExceptionHandler(h);
    }

    public void run() {
        try {
            _ssj.options.bufferSize.set(10.0f);
            _ssj.options.countdown.set(1);
            _ssj.options.log.set(true);

            //** connection to sensors
            Microphone mic = new Microphone();
            AudioChannel audio = new AudioChannel();
            audio.options.sampleRate.set(16000);
            audio.options.scale.set(true);
            _ssj.addSensor(mic,audio);

            //** transform data coming from sensors
            Pitch pitch = new Pitch();
            pitch.options.detector.set(Pitch.YIN);
            pitch.options.computePitchedState.set(false);
            pitch.options.computePitch.set(true);
            pitch.options.computeVoicedProb.set(false);
            pitch.options.computePitchEnvelope.set(false);
            _ssj.addTransformer(pitch, audio, 0.032, 0); //512 samples

            //** configure GUI
            //paint audio
            SignalPainter paint = new SignalPainter();
            paint.options.manualBounds.set(true);
            paint.options.min.set(0.);
            paint.options.max.set(1.);
            paint.options.renderMax.set(true);
            paint.options.secondScaleMin.set(0.);
            paint.options.secondScaleMax.set(500.);
            paint.options.graphView.set(_graphs[0]);
            _ssj.addConsumer(paint, new Provider[]{audio, pitch}, 0.032, 0);
        } catch(Exception e) {
            Log.e("SSJ_Demo", "error building pipe", e);
            return;
        }

        Log.i("SSJ_Demo", "starting pipeline");
        _ssj.start();
        _act.notifyPipeState(true);

        while(!_terminate) {
            try {
                synchronized(this) {
                    this.wait();
                }
            } catch (InterruptedException e) {
                Log.e("pipeline", "Error", e);
            }
        }

        Log.i("SSJ_Demo", "stopping pipeline");
        _ssj.stop();
        _ssj.clear();
        _act.notifyPipeState(false);
    }

    public void terminate() {
        _terminate = true;

        synchronized(this) {
            this.notify();
        }
    }

    public boolean isRunning() {
        return _ssj.isRunning();
    }
}
