package com.r2g.demo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class DemoProyectReactApplication implements CommandLineRunner {

	private static List<String> platos=new ArrayList<>();
	private static final Logger Log=LoggerFactory.getLogger(DemoProyectReactApplication.class);
	
	public static void main(String[] args) {
		platos.add("Hamburguesa");
		platos.add("Pitza");
		platos.add("Tacos");
		SpringApplication.run(DemoProyectReactApplication.class, args);
	}

	public void crearMono() {
		int x=30;
		Mono.just(x).subscribe(e->Log.info("Contenido: "+e));
	}

	public void crearFlux() {
		Flux<String> fxPlatos=Flux.fromIterable(platos);
		//fxPlatos.subscribe(p->Log.info("Platos "+p));
	fxPlatos.collectList().subscribe(lista->Log.info(lista.toString()));
	}
	public void m1doOnNext() {
		Flux<String> fxPlatos=Flux.fromIterable(platos);
		fxPlatos
		.doOnNext(p->Log.info(p))
		.subscribe();
	}
	public void m2map() {
		Flux<String> fxPlatos=Flux.fromIterable(platos);
		/*fxPlatos.map(p->"platos: "+p);
		fxPlatos.subscribe(p->Log.info(p));*/
		/*Flux<String> fx=fxPlatos.map(p->"platos: "+p);
		fx.subscribe(p->Log.info(p));*/
		fxPlatos.map(p->"platos: "+p).subscribe(p->Log.info(p));
	}
	
	public void m3flatMap() {
		/*Mono.just("Ronald")
		.flatMap(e->Mono.just(e+"Ramos"))
		.subscribe(e->Log.info(e));*/
		Mono.just("Ronald").map(x->30).subscribe(p->Log.info(p.toString()));
		Mono.just("Ronald").map(x->Mono.just(30)).subscribe(p->Log.info(p.toString()));
		Mono.just("Ronald").flatMap(x->Mono.just(30)).subscribe(p->Log.info(p.toString()));
		
	}
	public void m4range() {
		Flux<Integer> fx1=Flux.range(0, 10);
		fx1
		.map(x->x+1)
		.subscribe(x->Log.info(x.toString()));
}
	public void m5delayElements() throws InterruptedException {
		Flux.range(0, 10)
		.delayElements(Duration.ofSeconds(2))
		.doOnNext(i->Log.info(i.toString()))
		.subscribe();
		
		Thread.sleep(20000);
}
	public void m6zipWith() {
	List<String> clientes=new ArrayList<>();
	clientes.add("Ronald");
	clientes.add("Ramos");
	
	Flux<String> fxPlatos=Flux.fromIterable(platos);
	Flux<String> fxClientes=Flux.fromIterable(clientes);
	
	fxPlatos
	 .zipWith(fxClientes,(p,c)->String.format("Flux1: %s,Flux2: %s", p,c))
	 .subscribe(x->Log.info(x));
	}
	public void m7merge() {
		List<String> clientes=new ArrayList<>();
		clientes.add("Ronald");
		clientes.add("Ramos");
		clientes.add("Gutierrez");
		
		Flux<String> fxPlatos=Flux.fromIterable(platos);
		Flux<String> fxClientes=Flux.fromIterable(clientes);
		
		fxPlatos
		 .merge(fxPlatos,fxClientes)
		 .subscribe(x->Log.info(x));
		}
	public void m8filter() {
		Flux<String> fxPlatos=Flux.fromIterable(platos);
		fxPlatos
		.filter(p->p.startsWith("H"))
		.subscribe(x->Log.info(x));
	}
	public void m9take() {
		Flux<String> fxPlatos=Flux.fromIterable(platos);
		fxPlatos
		.take(2)
		.subscribe(x->Log.info(x));
	}
	
	public void m10takeLast() {
		Flux<String> fxPlatos=Flux.fromIterable(platos);
		fxPlatos
		.takeLast(2)
		.subscribe(x->Log.info(x));
	}
	
	public void m11DefaultIfEmpty() {
		platos=new ArrayList<>();
		Flux<String> fxPlatos=Flux.fromIterable(platos);
		fxPlatos
		.defaultIfEmpty("Lista Vacia")
		.subscribe(x->Log.info(x));
	}
	public void m12onErrorReturn() {
		
		Flux<String> fxPlatos=Flux.fromIterable(platos);
		
		fxPlatos
		.doOnNext(P->{
			//Calculo Aritmetico
			throw new ArithmeticException("MAL CALCULO");
		})
		.onErrorMap(ex->new Exception(ex.getMessage()))
		.subscribe(x->Log.info(x));
	}
public void m13retry() {
		
		Flux<String> fxPlatos=Flux.fromIterable(platos);
		
		fxPlatos
		.doOnNext(P->{
			Log.info("Intentando...");
			//Calculo Aritmetico
			throw new ArithmeticException("MAL CALCULO");
		})
		.retry(3)
		.onErrorReturn("ERROR")
		.subscribe(x->Log.info(x));
	}

	@Override
	public void run(String... args) throws Exception {
		//crearMono();
		//crearFlux();
		//m1doOnNext();
		//m2map();
		//m3flatMap();
		//m4range();
		//m5delayElements();
		//m6zipWith();
		//m7merge();
		//m8filter();
		//m9take();
		//m10takeLast();
		//m11DefaultIfEmpty();
		//m12onErrorReturn();
		m13retry();
	}
	
}
