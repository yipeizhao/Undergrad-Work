a=input('Give the starting interval:');
b=input('Give the end interval:');
c=input('Acceptable error:');
d=input('Max number of iteration:');
f=@(x)exp(x)-sin(x);
rootFound=0;
hv=ones(d);
for k=1:d
    if rootFound==0
   e=(a+b)/2;
   if sign(f(a))~=sign(f(e))
       b=e;
       diff=abs(f(a)-f(e));
   else
       a=e;
       diff=abs(f(b)-f(e));
   end
   hv(k)=e;
   if diff<=c
     fprintf('The roots is %.3f',e);
     rootFound=1;
     break
   end
    end
end

abserror= abs(e - hv(1:k));
figure (1)
plot(1:k,abserror(:));
title('Abs error');
figure (2)
relativeerror=abs(abserror./e);
title('Relative error');
plot(1:k,relativeerror);

 fid = fopen('bisection.txt','w');
 fprintf(fid,'Iteration  Prediction  AbsError RelError\n' );
 for i = 1:k
     fprintf(fid,'%d         %.5f         %.5f       %.5f\n',i,hv(i),abserror(i),relativeerror(i));
 end
 fclose(fid);