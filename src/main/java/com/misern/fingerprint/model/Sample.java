package com.misern.fingerprint.model;


import lombok.*;

import javax.annotation.Generated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sample {
	private Long id;
	private Double lastTime;
	private Double measuredTime;
	private String userName;
}
