/*
 *
  Copyright (c) <2011>, <Shigeo Yoshida>
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
The names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.yadrone.base.video;

//import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.util.ArrayList;


import de.yadrone.base.command.CommandManager;
import de.yadrone.base.command.VideoBitRateMode;
import de.yadrone.base.manager.AbstractTCPManager;
import de.yadrone.base.utils.ARDroneUtils;

public class VideoManager extends AbstractTCPManager {
	private VideoDecoder decoder;

	private CommandManager manager = null;

	private ArrayList<ImageListener> listener = new ArrayList<ImageListener>();

	public VideoManager(InetAddress inetaddr, CommandManager manager, VideoDecoder decoder) {
		super(inetaddr);
		this.manager = manager;
		this.decoder = decoder;
	}

	public void addImageListener(ImageListener listener) {
		this.listener.add(listener);
		//if (this.listener.size() == 1)
			//decoder.setImageListener(this);
	}
	
	public void removeImageListener(ImageListener listener) {
		this.listener.remove(listener);
		if (this.listener.size() == 0)
			decoder.setImageListener(null);
	}

	/** Called only by decoder to inform all the other listener 
	public void imageUpdated(BufferedImage image)
	{
		for (int i=0; i < listener.size(); i++)
		{
			listener.get(i).imageUpdated(image);
		}
	}
	*/
	public boolean connect(int port) {
		if (decoder == null)
			return false;

		return super.connect(port);
	}

	@Override
	public void run() {
		if (decoder == null)
			return;
		connect(ARDroneUtils.VIDEO_PORT);
		ticklePort(ARDroneUtils.VIDEO_PORT);
		
		manager.setVideoBitrateControl(VideoBitRateMode.DISABLED); // bitrate set to maximum
		
		decoder.decode(getInputStream());
		
		close();
	}

	@Override
	public void close() {
		if (decoder == null)
			return;

		super.close();
	}

}
